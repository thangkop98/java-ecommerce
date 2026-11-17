# syntax=docker/dockerfile:1

# ---------- Stage 1: build ----------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /build

# Copy pom.xml trước để cache dependency: chỉ tải lại khi pom.xml thay đổi
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -B -q dependency:go-offline

COPY src ./src
# Bỏ qua test khi build image (test cần DB thật); test nên chạy ở bước CI riêng
RUN --mount=type=cache,target=/root/.m2 mvn -B -q package -DskipTests \
    && cp target/*.jar app.jar \
    # Tách jar thành các layer: dependencies ít thay đổi -> cache tốt hơn khi push/pull image
    && java -Djarmode=tools -jar app.jar extract --layers --destination extracted

# ---------- Stage 2: runtime ----------
FROM eclipse-temurin:25-jre
WORKDIR /app

# curl dùng cho HEALTHCHECK; chạy bằng user thường, không dùng root
RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system spring && useradd --system --gid spring --no-create-home spring \
    && mkdir -p /app/data/uploads/product /app/data/uploads/user \
    && chown -R spring:spring /app/data

COPY --from=build --chown=spring:spring /build/extracted/dependencies/ ./
COPY --from=build --chown=spring:spring /build/extracted/spring-boot-loader/ ./
COPY --from=build --chown=spring:spring /build/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=spring:spring /build/extracted/application/ ./

USER spring
# 8080: web app, 8081: actuator (health, metrics) - chỉ dùng nội bộ
EXPOSE 8080 8081

# Readiness = app đã start xong và kết nối được DB
HEALTHCHECK --interval=15s --timeout=5s --start-period=60s --retries=3 \
    CMD curl -fs http://localhost:8081/actuator/health/readiness | grep -q '"UP"' || exit 1

# Dùng tối đa 75% RAM được cấp cho container
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
