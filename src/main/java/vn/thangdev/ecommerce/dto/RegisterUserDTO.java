package vn.thangdev.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
	@NotBlank(message = "Vui lòng nhập họ tên")
	private String name;

	@NotBlank(message = "Vui lòng nhập email")
	@Email(message = "Email không hợp lệ")
	private String email;

	@NotBlank(message = "Vui lòng nhập mật khẩu")
	@Size(min = 8, message = "Mật khẩu phải chứa tối thiểu 8 ký tự")
	private String password;

	@NotBlank(message = "Vui lòng xác nhận mật khẩu")
	private String confirmPassword;

	@NotBlank(message = "Vui lòng nhập số điện thoại")
	private String phone;

	private String address;
}
