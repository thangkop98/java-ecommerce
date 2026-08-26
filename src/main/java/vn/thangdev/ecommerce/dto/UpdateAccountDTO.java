package vn.thangdev.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO {
	private Long id;

	@NotBlank(message = "Vui lòng nhập họ tên")
	private String name;

	@NotBlank(message = "Vui lòng nhập email")
	@Email(message = "Email không hợp lệ")
	private String email;

	@NotBlank(message = "Vui lòng nhập số điện thoại")
	private String phone;

	private String avatar;
}
