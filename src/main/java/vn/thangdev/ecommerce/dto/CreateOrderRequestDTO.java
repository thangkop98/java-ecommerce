package vn.thangdev.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {
	@NotBlank(message = "Vui lòng điền họ tên")
	private String username;

	@NotBlank(message = "Vui lòng điền địa chỉ nhận hàng")
	private String address;

	@NotBlank(message = "Vui lòng điền số điện thoại")
	private String phone;
}
