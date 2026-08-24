# Dự án LaptopShop
- Một ứng dụng web thương mại điện tử full-stack được xây dựng bằng Java và Spring Boot. Dự án này cung cấp một quy trình thương mại điện tử hoàn chỉnh bao gồm xác thực người dùng, duyệt sản phẩm, giỏ hàng, đặt hàng, quản lý đơn hàng và quản trị.

## 1. Tổng quan
- Ứng dụng LaptopShop là một ứng dụng web thương mại điện tử được phát triển bằng Java và Spring Boot.
- Ứng dụng cho phép khách hàng duyệt sản phẩm, tìm kiếm và lọc sản phẩm, quản lý giỏ hàng, đặt hàng và xem lịch sử đơn hàng.
- Dự án cũng bao gồm khu vực quản trị để quản lý sản phẩm, danh mục, thương hiệu, đơn hàng và các dữ liệu liên quan đến thương mại điện tử khác.
- Mục đích chính của dự án này là thực hành và chứng minh việc phát triển phần backend với Spring Boot, Spring MVC, Spring Data JPA, Spring Security, thiết kế cơ sở dữ liệu và render dữ liệu ở phía máy chủ với Thymeleaf.

## 2. Tính năng
### 2.1. Phía khách hàng (user)
- Đăng ký tài khoản
- Đăng nhập/đăng xuất
- Xem sản phẩm, lọc sản phẩm
- Xem, quản lý giỏ hàng
- Đặt hàng
- Quản lý tài khoản
- Xem lịch sử mua hàng

### 2.2. Phía quản trị (admin)
- Xem Dashboard
- Quản lý người dùng
- Quản lý danh mục sản phẩm
- Quản lý sản phẩm
- Quản lý đơn hàng
- Quản lý hãng sản xuất

### 2.3. Sản phẩm ở trang enduser
- Hiển thị danh sách sản phẩm
- Chi tiết sản phẩm
- Lọc sản phẩm theo hãng sản xuất, mục đích sử dụng, giá tiền
- Phân trang

### 2.4. Giỏ hàng
- Thêm sản phẩm vào giỏ hàng
- Cập nhật số lượng sản phẩm
- Xóa sản phẩm
- Hiển thị số lượng sản phẩm trong giỏ
- Hiển thị số tiền của mỗi sản phẩm
- Tính tổng tiền của các sản phẩm trong giỏ hàng

### 2.5. Đơn hàng
- Checkout và xác thực thông tin đặt hàng
- Tạo đơn hàng, chi tiết đơn hàng
- Hiển thị thông tin xác nhận đơn hàng

### 2.6. Tương tác phía client
- Thêm sản phẩm vào giỏ hàng bằng AJAX
- Cập nhật số lượng sản phẩm
- Cập nhật số lượng giỏ hàng mà không cần reload trang
- Hiển thị thông báo bằng toastr

## 3. Công nghệ dùng trong dự án
### 3.1. Backend
- Java 25
- Spring Boot 4.1.0
- Spring MVC
- Spring Data JPA/Hibernate
- Spring Security
- Maven

### 3.2. Frontend
- Thymeleaf
- HTML5
- CSS
- JavaScript
- Bootstrap

### 3.3. Database
- MySQL 8.0.46

### 3.4. Công cụ phát triển
- Spring Tool Suite (STS)
- Git, GitHub
- Maven

## 4. Kiến trúc dự án
Dự án tuân theo kiến trúc phân lớp: Controller -> Service -> Repository -> Database
- Controller: xử lý request và trả về response
- Service: xử lý logic nghiệp vụ
- Repository: xử lý truy cập database sử dụng Spring Data JPA
- Entity: đại diện cho bảng trong database
- DTO: truyền dữ liệu giữa các lớp và xử lý xác thực
- Spring Security: xử lý xác thực và phân quyền

 ## 5. Yêu cầu cần có để chạy dự án
Trước khi chạy dự án, đảm bảo những phần mềm sau được cài đặt:
- JDK 25
- MySQL 8.0.46 hoặc cao hơn
- Git
- IDE: Cài 1 trong các tool sau Spring tool suite/VS code/IntelliJ IDEA/Eclipse, ...
- Tool quản lý cơ sở dữ liệu: Có thể tạo database bằng command line hoặc cài 1 trong các tool sau Navicat/MySQL Workbench/DBeaver, ...

Không cần cài đặt Maven toàn cục vì dự án đã bao gồm Maven Wrapper.

## 6. Hướng dẫn cài đặt và chạy dự án
### 6.1. Clone source code dự án
- Mở terminal, di chuyển vào thư mục bạn muốn cài dự án, chạy lệnh: git clone https://github.com/thangkop98/java-ecommerce.git

### 6.2. Mở dự án
- chạy lệnh: cd java-ecommerce

### 6.3. Cấu hình kết nối tới database
Ứng dụng sử dụng MySQL với thông tin kết nối mặc định:

| Configuration | Default value |
|---|---|
| Database URL | `jdbc:mysql://localhost:3306/java-ecommerce` |
| Username | `root` |
| Password | `root` |

Mở MySQL Workbench, DBeaver, Navicat hoặc công cụ quản lý MySQL khác.

Tạo database với tên: java-ecommerce, sau đó import file: database/java-ecommerce.sql vào database java-ecommerce, file SQL chứa cấu trúc bảng và dữ liệu mẫu của ứng dụng

<img width="593" height="553" alt="image" src="https://github.com/user-attachments/assets/b189a2d7-839b-43f6-ae7a-7a7e02f0e1b8" />

Để cấu hình kết nối ứng dụng tới database, mở file: src/main/resources/application.properties, kiểm tra và cập nhật thông tin kết nối MySQL:
- spring.datasource.url=jdbc:mysql://localhost:3306/java-ecommerce
- spring.datasource.username=root
- spring.datasource.password=root
  
Trong đó:
- localhost: địa chỉ MySQL server trên máy local.
- 3306: port mặc định của MySQL.
- java-ecommerce: tên database.
- root: tên username và password mặc định.

Nếu thông tin kết nối MySQL trên máy bạn khác với cấu hình trên, hãy sửa spring.datasource.username và spring.datasource.password trong application.properties

### 6.4. Build dự án
Dự án sử dụng Maven Wrapper, vì vậy không cần cài Maven ở phạm vi toàn cục
+ Với máy windows, chạy lệnh: mvnw.cmd clean package hoặc ./mvnw.cmd clean package
+ Với máy linux/macOS, chạy lệnh: ./mvnw clean package

### 6.5. Chạy dự án
#### 6.5.1. Chạy dự án với Maven
- Máy windows, chạy lệnh: mvnw.cmd spring-boot:run (CMD/PowerShell) hoặc ./mvnw.cmd spring-boot:run (git bash)
- Máy linux/macOS: chạy lệnh: ./mvnw spring-boot:run

#### 6.5.2. Chạy dự án với tool IDE
Với Spring tool suite (STS):
+ Bước 1: Mở STS
+ Bước 2: Chọn File -> Open Projects From File System... -> chọn Directory -> chọn thư mục dự án
+ Bước 3: Chọn Finish
+ Bước 4: Đợi Maven dependencies download xong, quá trình này diễn ra tự động, hoặc làm thủ công bằng cách click chuột phải vào dự án -> chọn Maven -> Update Project ... -> chọn OK
+ Bước 5: Click chuột phải vào dự án -> chọn Run As -> Chọn Spring Boot App

Với VS Code:
+ Bước 1: cài các extension cần thiết để hỗ trợ chạy Java
+ Bước 2: Mở thư mục chứa dự án
+ Bước 3: Đợi Maven dependencies download xong, quá trình này diễn ra tự động
+ Bước 4: Chạy dự án, nếu cài extension spring boot extension pack thì dùng spring boot dashboard ở phần sidebar để chạy
<img width="690" height="701" alt="image" src="https://github.com/user-attachments/assets/3e939cc1-e54a-469e-bcad-aef4770283f6" />
<img width="684" height="730" alt="image" src="https://github.com/user-attachments/assets/975fe3b3-b27a-4bd6-9d11-4e5de9bec20b" />

#### 6.5.3. Truy cập ứng dụng
Sau khi ứng dụng khởi động thành công, truy cập: http://localhost:8080

#### 6.5.4. Các URL chính
- Trang khách hàng: http://localhost:8080
- Trang login: http://localhost:8080/login
- Trang admin: http://localhost:8080/admin

#### 6.5.5. Tài khoản demo
Khách hàng:
- username/email: user@gmail.com
- password: 12345678

Admin:
- username/email: admin@gmail.com
- password: 12345678

## 7. Cấu trúc thư mục dự án
<img width="599" height="476" alt="image" src="https://github.com/user-attachments/assets/a7ab44f1-5faa-4068-88ce-a491a64bb27d" />

## 8. Screenshots
### Trang chủ
<img width="1897" height="974" alt="image" src="https://github.com/user-attachments/assets/a70b64ab-bfe0-4610-bc99-0ced77d2d038" />

### Danh sách sản phẩm
<img width="1902" height="973" alt="image" src="https://github.com/user-attachments/assets/524d0184-ca2a-43da-abfd-afa4e2466f33" />

### Chi tiết sản phẩm
<img width="1867" height="968" alt="image" src="https://github.com/user-attachments/assets/fc9d5a49-84af-40fb-bac8-ff2e46fa8d6b" />

### Giỏ hàng
<img width="1905" height="970" alt="image" src="https://github.com/user-attachments/assets/25fe9fab-efc5-43c0-8aed-1b80e68647a2" />

### Đặt hàng
<img width="1899" height="973" alt="image" src="https://github.com/user-attachments/assets/67537492-c9ba-443a-af1d-a37f0adfdcf6" />
<img width="1899" height="840" alt="image" src="https://github.com/user-attachments/assets/33bd69c3-4722-47ad-86f3-144d53ef148c" />

### Lịch sử mua hàng
<img width="1896" height="970" alt="image" src="https://github.com/user-attachments/assets/ab6b1857-cf2a-4027-9aa0-ae2dbc5b2b1f" />

### Admin dashboard
<img width="1909" height="971" alt="image" src="https://github.com/user-attachments/assets/996721b0-a6ae-4086-b02b-db94e7230333" />

### Quản lý sản phẩm
<img width="1900" height="970" alt="image" src="https://github.com/user-attachments/assets/2836d8f5-74c5-4a63-91e5-dfe383fe7f1b" />


## 9. Tác giả
**<Hoàng Vũ Thắng>**

- GitHub: https://github.com/thangkop98
- Email: hoangvuthang270898@gmail.com

