/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80046 (8.0.46)
 Source Host           : localhost:3306
 Source Schema         : java-ecommerce

 Target Server Type    : MySQL
 Target Server Version : 80046 (8.0.46)
 File Encoding         : 65001

 Date: 26/08/2026 18:01:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brands
-- ----------------------------
DROP TABLE IF EXISTS `brands`;
CREATE TABLE `brands`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKoce3937d2f4mpfqrycbr0l93m`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brands
-- ----------------------------
INSERT INTO `brands` VALUES (1, '2026-08-26 14:22:12.059730', 'Apple', 'active', '2026-08-26 14:22:12.059730');
INSERT INTO `brands` VALUES (2, '2026-08-26 14:22:17.528553', 'Asus', 'active', '2026-08-26 14:22:17.528553');
INSERT INTO `brands` VALUES (3, '2026-08-26 14:22:21.934083', 'Lenovo', 'active', '2026-08-26 14:22:21.934083');
INSERT INTO `brands` VALUES (4, '2026-08-26 14:22:26.957021', 'Dell', 'active', '2026-08-26 14:22:26.957021');
INSERT INTO `brands` VALUES (5, '2026-08-26 14:22:31.905153', 'LG', 'active', '2026-08-26 14:22:31.905153');
INSERT INTO `brands` VALUES (6, '2026-08-26 14:22:36.547869', 'Acer', 'active', '2026-08-26 17:05:46.429901');

-- ----------------------------
-- Table structure for cart_details
-- ----------------------------
DROP TABLE IF EXISTS `cart_details`;
CREATE TABLE `cart_details`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `quantity` int NOT NULL,
  `updated_at` datetime(6) NULL DEFAULT NULL,
  `cart_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_cart_product`(`cart_id` ASC, `product_id` ASC) USING BTREE,
  INDEX `FK9rlic3aynl3g75jvedkx84lhv`(`product_id` ASC) USING BTREE,
  CONSTRAINT `FK9rlic3aynl3g75jvedkx84lhv` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKkcochhsa891wv0s9wrtf36wgt` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart_details
-- ----------------------------

-- ----------------------------
-- Table structure for carts
-- ----------------------------
DROP TABLE IF EXISTS `carts`;
CREATE TABLE `carts`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK64t7ox312pqal3p7fg9o503c2`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carts
-- ----------------------------

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKt8o6pivur7nn124jehx7cygw5`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '2026-08-26 14:21:01.572560', 'Laptop', 'active', '2026-08-26 14:21:01.572560');
INSERT INTO `categories` VALUES (2, '2026-08-26 14:21:07.734737', 'Phụ kiện', 'active', '2026-08-26 14:21:07.734737');
INSERT INTO `categories` VALUES (3, '2026-08-26 14:21:14.077432', 'Điện thoại', 'active', '2026-08-26 14:21:14.077432');
INSERT INTO `categories` VALUES (4, '2026-08-26 14:21:20.298610', 'Màn hình', 'active', '2026-08-26 14:21:53.894363');

-- ----------------------------
-- Table structure for order_details
-- ----------------------------
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(19, 2) NOT NULL,
  `unit_price` decimal(19, 2) NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKjyu2qbqt8gnvno9oe9j2s2ldk`(`order_id` ASC) USING BTREE,
  INDEX `FK4q98utpd73imf4yhttm3w0eax`(`product_id` ASC) USING BTREE,
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_details
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `receiver_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiver_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `receiver_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('cancelled','confirm','delivered','pending','shipping') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `total_amount` decimal(19, 2) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKgt3o4a5bqj59e9y6wakgk926t`(`code` ASC) USING BTREE,
  INDEX `FK32ql8ubntj5uh44ph9659tiih`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for product_usages
-- ----------------------------
DROP TABLE IF EXISTS `product_usages`;
CREATE TABLE `product_usages`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `usage_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_product_usage`(`product_id` ASC, `usage_id` ASC) USING BTREE,
  INDEX `FKgprtdgiv9jkxd4a91gy11usvx`(`usage_id` ASC) USING BTREE,
  CONSTRAINT `FKg7g083ok7nv7hgay2rw2o2bj7` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FKgprtdgiv9jkxd4a91gy11usvx` FOREIGN KEY (`usage_id`) REFERENCES `useages` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_usages
-- ----------------------------
INSERT INTO `product_usages` VALUES (1, 1, 1);
INSERT INTO `product_usages` VALUES (2, 1, 5);
INSERT INTO `product_usages` VALUES (3, 2, 1);
INSERT INTO `product_usages` VALUES (4, 2, 3);
INSERT INTO `product_usages` VALUES (5, 2, 4);
INSERT INTO `product_usages` VALUES (6, 3, 3);
INSERT INTO `product_usages` VALUES (7, 3, 5);
INSERT INTO `product_usages` VALUES (8, 4, 2);
INSERT INTO `product_usages` VALUES (9, 4, 5);
INSERT INTO `product_usages` VALUES (10, 5, 2);
INSERT INTO `product_usages` VALUES (11, 5, 5);
INSERT INTO `product_usages` VALUES (12, 6, 2);
INSERT INTO `product_usages` VALUES (13, 6, 3);
INSERT INTO `product_usages` VALUES (14, 6, 5);
INSERT INTO `product_usages` VALUES (15, 7, 4);
INSERT INTO `product_usages` VALUES (16, 7, 5);
INSERT INTO `product_usages` VALUES (17, 8, 1);
INSERT INTO `product_usages` VALUES (18, 8, 4);
INSERT INTO `product_usages` VALUES (19, 9, 2);
INSERT INTO `product_usages` VALUES (20, 9, 3);
INSERT INTO `product_usages` VALUES (21, 9, 4);
INSERT INTO `product_usages` VALUES (25, 10, 1);
INSERT INTO `product_usages` VALUES (26, 10, 2);
INSERT INTO `product_usages` VALUES (27, 10, 5);
INSERT INTO `product_usages` VALUES (28, 11, 1);
INSERT INTO `product_usages` VALUES (29, 11, 3);
INSERT INTO `product_usages` VALUES (30, 11, 4);
INSERT INTO `product_usages` VALUES (31, 12, 3);
INSERT INTO `product_usages` VALUES (32, 12, 5);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(15, 2) NOT NULL,
  `sku` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `slug` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `stock_quantity` int NOT NULL,
  `thumbnail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `brand_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKfhmd06dsmj6k0n90swsh8ie9g`(`sku` ASC) USING BTREE,
  UNIQUE INDEX `UKostq1ec3toafnjok09y9l7dox`(`slug` ASC) USING BTREE,
  INDEX `FKa3a4mpsfdf4d2y6r8ra3sc8mv`(`brand_id` ASC) USING BTREE,
  INDEX `FKog2rp4qthbtt2lfyhfo32lsw9`(`category_id` ASC) USING BTREE,
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, '2026-08-26 15:36:14.964122', 'Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU sở hữu màn hình 16 inch Full HD+, RAM 16GB DDR5 tốc độ 4800MHz (hỗ trợ nâng cấp tối đa 64GB). Chiếc laptop Acer Aspire được trang bị Intel Core i7-1355U, card đồ họa Intel Iris Xe, cùng loa Stereo và webcam Full HD. Thiết kế gọn nhẹ 1.7kg, tích hợp đầy đủ cổng kết nối giúp sử dụng linh hoạt.', 'Laptop Acer Aspire', 23490000.00, 'PRD-64B6861D', 'laptop-acer-aspire', 'active', 100, '/uploads/product/bd64375d-005d-4517-b704-54baaa23fc9b.png', '2026-08-26 15:36:14.964122', 6, 1);
INSERT INTO `products` VALUES (2, '2026-08-26 15:37:01.142393', 'Laptop Acer Gaming Nitro V 15 ProPanel ANV15-52-50VA đem đến trải nghiệm giải trí sống động, ấn tượng với màn hình ProPanel 180 Hz và độ phủ màu 100% sRGB. Máy trang bị vi xử lý Intel Core 5 processor 210H thế hệ mới cho hiệu năng mạnh mẽ. Ngoài ra, thiết bị còn trang bị RAM DDR5 tốc độ cao hỗ trợ đa nhiệm linh hoạt.', 'Laptop Acer Gaming Nitro', 28990000.00, 'PRD-84BF1FA2', 'laptop-acer-gaming-nitro', 'active', 30, '/uploads/product/329c6695-0a6a-4fed-a976-60b65e086bea.png', '2026-08-26 15:37:01.142393', 6, 1);
INSERT INTO `products` VALUES (3, '2026-08-26 15:38:08.988883', 'MacBook Neo 13 A18 Pro 256GB là dòng máy tính xách tay đột phá sở hữu sức mạnh từ chip Apple A18 Pro đi kèm với 6 lõi CPU và 5 lõi GPU. Thiết bị duy trì khả năng đa nhiệm mượt mà nhờ bộ nhớ thống nhất 8GB và ổ cứng SSD 256GB. Màn hình Liquid Retina rực rỡ mang lại trải nghiệm thị giác sống động.', 'MacBook Neo 13', 18790000.00, 'PRD-D38C14A4', 'macbook-neo-13', 'active', 49, '/uploads/product/1caabbeb-06ff-4a06-a5f2-2a74fcde8cbc.png', '2026-08-26 15:38:08.988883', 1, 1);
INSERT INTO `products` VALUES (4, '2026-08-26 15:38:54.785645', 'MacBook Air 13 M5 10CPU 8GPU 16GB 512GB được trang bị chip Apple M5 với CPU 10 lõi, GPU 8 lõi hỗ trợ ray tracing phần cứng và Neural Engine 16 lõi. Máy sở hữu RAM 16GB, SSD 512GB cùng màn hình Liquid Retina 13.6 inch. Thiết kế nhôm nguyên khối dày 1,13cm và pin 53,8Wh cho thời lượng xem video lên đến 18 giờ.', 'MacBook Air M5', 35490000.00, 'PRD-87618147', 'macbook-air-m5', 'active', 78, '/uploads/product/d0054726-401b-478e-be64-2e7604127c7d.png', '2026-08-26 15:38:54.785645', 1, 1);
INSERT INTO `products` VALUES (5, '2026-08-26 15:39:54.419495', 'Laptop Lenovo LOQ Essential 15ARP10E 83S0000DVN trang bị vi xử lý AMD Ryzen 5 7535HS hỗ trợ tính toán và phân bổ tài nguyên hệ thống diễn ra hiệu quả. Card đồ họa tiên tiến hỗ trợ hiển thị hình ảnh sắc nét và duy trì hiệu suất ổn định. Màn hình 15.6 inch 144Hz tối ưu hóa trải nghiệm thị giác với độ chi tiết cao.', 'Laptop Lenovo LOQ Essential', 25490000.00, 'PRD-23712A80', 'laptop-lenovo-loq-essential', 'active', 89, '/uploads/product/5d9589bd-d3ab-4a41-95fc-0e7d4be885db.png', '2026-08-26 15:39:54.419495', 3, 1);
INSERT INTO `products` VALUES (6, '2026-08-26 15:41:13.915213', 'Màn hình Lenovo Legion R27S 68CCGAC1VN 27 inch được trang bị độ phân giải Full HD, tấm nền IPS và tần số quét 144Hz, giúp hình ảnh hiển thị mượt mà. Màn hình máy tính Lenovo đạt 99% sRGB, hỗ trợ HDR10 giúp tối ưu độ tương phản. Thiết kế hiện đại tích hợp Eye Comfort, phù hợp cho chơi game và làm việc hằng ngày.', 'Màn hình gaming Lenovo Legion', 2790000.00, 'PRD-10147EC2', 'man-hinh-gaming-lenovo-legion', 'active', 15, '/uploads/product/43ba0597-a163-46f9-ab1c-d9d3297627bb.png', '2026-08-26 15:41:13.915213', 3, 4);
INSERT INTO `products` VALUES (7, '2026-08-26 15:42:14.735398', 'Laptop Dell 16 Plus DB16250 DB6U5387W1 được trang bị CPU Intel Core Ultra 5 226V mạnh mẽ, đi cùng bộ RAM có dung lượng 16GB và ổ cứng 1TB rộng rãi. Thêm vào đó, màn hình 16 inch còn cung cấp độ phân giải 2.5K sắc nét. Ngoài ra, pin 64Whr cũng mang đến thời lượng hoạt động nhiều giờ tùy nhu cầu.', 'Laptop Dell 16 Plus', 35990000.00, 'PRD-F3165882', 'laptop-dell-16-plus', 'active', 66, '/uploads/product/c1cf78af-831b-4201-9d23-b9095795748f.png', '2026-08-26 15:42:14.735398', 4, 1);
INSERT INTO `products` VALUES (8, '2026-08-26 15:43:13.929338', 'Laptop Dell 15 DC15255 G8MK9 được trang bị vi xử lý Ryzen 7 7730U (8 nhân, 16 luồng), đi kèm RAM 16GB và SSD 512GB, mang lại khả năng xử lý đa nhiệm ổn định. Máy sở hữu màn hình 15.6 inch Full HD cảm ứng với tấm nền IPS. Máy có thiết kế nhựa, trọng lượng 1.90kg, pin 41Wh, hỗ trợ đầy đủ cổng kết nối và chạy Windows 11.', 'Laptop Dell 15 DC15255', 18990000.00, 'PRD-6DB85BB0', 'laptop-dell-15-dc15255', 'active', 54, '/uploads/product/7eea34ce-8bee-4160-8424-20555fbbf579.png', '2026-08-26 15:43:13.929338', 4, 1);
INSERT INTO `products` VALUES (9, '2026-08-26 15:44:32.563251', 'Laptop Asus Vivobook 15 X1504VA-BQ165W được trang bị vi xử lý Intel Core 5 120U, kết hợp RAM 16GB và SSD 512GB, mang lại hiệu suất ổn định khi sử dụng. Máy có màn hình 15.6 inch Full HD cùng lớp phủ chống chói, giảm mỏi mắt khi làm việc lâu. Laptop ASUS Vivobook này còn hỗ trợ Wi-Fi 6, Bluetooth 5.2 cùng nhiều cổng kết nối thông dụng tiện lợi.', 'Laptop ASUS Vivobook 15', 17990000.00, 'PRD-1B177346', 'laptop-asus-vivobook-15', 'active', 76, '/uploads/product/2f0aff10-2a67-4d22-952e-91adabc7d516.png', '2026-08-26 15:44:32.563251', 2, 1);
INSERT INTO `products` VALUES (10, '2026-08-26 15:45:13.823547', 'Laptop ASUS Zenbook A14 UX3407QA-QD299WS Copilot X Plus được trang bị bên trong vi xử lý Snapdragon X X1 26 100, cùng với card đồ hoạ Qualcomm Adreno GPU. Mẫu laptop Asus Zenbook này được trang bị 16GB Ram LPDDR5X, đi kèm là ổ cứng 512GB M.2 NVM PCIe 4.0. Màn hình rộng 14 inches tỉ lệ 16:10 có công nghệ TUV Rheinland-certified chống chói.', 'Laptop ASUS Zenbook A14', 28590000.00, 'PRD-9C3AE38E', 'laptop-asus-zenbook-a14', 'active', 43, '/uploads/product/b0fd2659-8505-4249-9d1e-91976af681be.png', '2026-08-26 15:45:31.436944', 2, 1);
INSERT INTO `products` VALUES (11, '2026-08-26 15:46:28.598022', 'Màn hình LG 24U411A-B 24 inch cho trải nghiệm hình ảnh chân thực với độ phân giải FHD (1920×1080), tần số quét 120Hz và thời gian phản hồi 5ms. Mẫu màn hình máy tính này được thiết kế siêu mỏng cùng khả năng điều chỉnh độ nghiêng linh hoạt. Sản phẩm trang bị D-Sub, HDMI và jack tai nghe, đáp ứng nhu cầu kết nối cơ bản.', 'Màn hình LG 24U411A-B', 2390000.00, 'PRD-38928400', 'man-hinh-lg-24u411a-b', 'active', 23, '/uploads/product/48a36445-bb1a-4f02-ae27-d37788658cb9.png', '2026-08-26 15:46:28.598022', 5, 4);
INSERT INTO `products` VALUES (12, '2026-08-26 15:47:19.222165', 'Smart Tivi LG UHD 4K 65 inch 65UA8055PSA sử dụng công nghệ hiển thị HDR10 Pro, đi cùng con chip AI alpha 7 4K Gen8 mới góp phần tối ưu hóa hình ảnh. Thêm vào đó, chiếc Tivi LG này còn hỗ trợ các tiện ích AI thông minh. Ngoài ra, kích thước màn hình 65 inch cũng đem lại không gian quan sát thoải mái.', 'Smart tivi LG UHD 4K', 13190000.00, 'PRD-A6ABA405', 'smart-tivi-lg-uhd-4k', 'active', 46, '/uploads/product/efc7ebe3-0306-46ad-9b41-ce1ea8bafd4f.png', '2026-08-26 15:47:19.222165', 5, 4);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` enum('ADMIN','USER') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKofx66keruapi6vyqpv6f2or37`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'ADMIN');
INSERT INTO `roles` VALUES (2, 'USER');

-- ----------------------------
-- Table structure for spring_session
-- ----------------------------
DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE `spring_session`  (
  `PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`) USING BTREE,
  UNIQUE INDEX `SPRING_SESSION_IX1`(`SESSION_ID` ASC) USING BTREE,
  INDEX `SPRING_SESSION_IX2`(`EXPIRY_TIME` ASC) USING BTREE,
  INDEX `SPRING_SESSION_IX3`(`PRINCIPAL_NAME` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of spring_session
-- ----------------------------

-- ----------------------------
-- Table structure for spring_session_attributes
-- ----------------------------
DROP TABLE IF EXISTS `spring_session_attributes`;
CREATE TABLE `spring_session_attributes`  (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`) USING BTREE,
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of spring_session_attributes
-- ----------------------------

-- ----------------------------
-- Table structure for useages
-- ----------------------------
DROP TABLE IF EXISTS `useages`;
CREATE TABLE `useages`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of useages
-- ----------------------------
INSERT INTO `useages` VALUES (1, '2026-08-26 14:23:19.840555', 'Gaming', 'active', '2026-08-26 14:23:54.705764');
INSERT INTO `useages` VALUES (2, '2026-08-26 14:23:25.447709', 'Sinh viên - văn phòng', 'active', '2026-08-26 14:23:25.447709');
INSERT INTO `useages` VALUES (3, '2026-08-26 14:23:32.200952', 'Thiết kế đồ họa', 'active', '2026-08-26 14:23:32.200952');
INSERT INTO `useages` VALUES (4, '2026-08-26 14:23:37.963232', 'Mỏng nhẹ', 'active', '2026-08-26 14:23:37.963232');
INSERT INTO `useages` VALUES (5, '2026-08-26 14:23:44.318421', 'Doanh nhân', 'active', '2026-08-26 14:23:44.318421');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK6dotkott2kjsp8vw4d0m25fb7`(`email` ASC) USING BTREE,
  INDEX `FKp56c1712k691lhsyewcssf40f`(`role_id` ASC) USING BTREE,
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '', '/uploads/user/d47958ea-0566-4876-a9a9-587cca650336.jpg', '2026-08-26 11:35:45.768774', 'admin@gmail.com', 'admin', '$2a$10$/1ucEdM4OJ5AeL9STZ2nm.EPDXL1CqCfDfa.UDZXUUmDSWMX.TVLa', '0987123455', 'active', '2026-08-26 11:36:32.335759', 1);
INSERT INTO `users` VALUES (2, '', '/uploads/user/1aa05807-dcfe-40ad-86ca-dfcfcaf61b43.jpg', '2026-08-26 11:35:45.874898', 'user@gmail.com', 'user', '$2a$10$c/O6zrTotsC07DRHRt8EFuVqCqwmZGr6.Q7cd.2Xw6OcyznUfw6c.', '0987123456', 'active', '2026-08-26 16:09:19.787455', 2);

SET FOREIGN_KEY_CHECKS = 1;
