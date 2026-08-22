package vn.thangdev.ecommerce.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import vn.thangdev.ecommerce.model.Order;

public class OrderSpec {
	public static Specification<Order> filterOrders(String code, String name, String status, String phone,
			String fromDate, String toDate) {
		return (root, query, builder) -> {
			List<Predicate> pre = new ArrayList<>();

			if (code != null && !code.isBlank()) {
				pre.add(builder.like(root.get("code"), "%" + code + "%"));
			}

			if (name != null && !name.isBlank()) {
				pre.add(builder.like(root.get("receiverName"), "%" + name + "%"));
			}

			if (status != null && !status.isBlank()) {
				pre.add(builder.equal(root.get("status"), status));
			}

			if (phone != null && !phone.isBlank()) {
				pre.add(builder.equal(root.get("receiverPhone"), phone));
			}

			if (fromDate != null && !fromDate.isBlank()) {
				LocalDate newFromDate = LocalDate.parse(fromDate);
				LocalDateTime startOfDay = newFromDate.atStartOfDay();
				pre.add(builder.greaterThanOrEqualTo(root.get("createdAt"), newFromDate));
			}

			if (toDate != null && !toDate.isBlank()) {
				LocalDate newToDate = LocalDate.parse(toDate);
				LocalDateTime endOfDay = newToDate.atTime(LocalTime.MAX);
				pre.add(builder.lessThanOrEqualTo(root.get("createdAt"), newToDate));
			}

			return builder.and(pre);
		};
	}
}
