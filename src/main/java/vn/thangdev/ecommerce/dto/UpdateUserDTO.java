package vn.thangdev.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.thangdev.ecommerce.model.Role;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDTO {
	private Long id;

	@NotBlank(message = "Vui lòng nhập họ tên")
	private String name;

	@NotBlank(message = "Vui lòng nhập email")
	@Email(message = "Email không hợp lệ")
	private String email;

	@NotBlank(message = "Vui lòng nhập số điện thoại")
	private String phone;

	private String address;

	@NotNull(message = "Vui lòng chọn vai trò")
	private Role role;

	@Pattern(regexp = "^$|.{8,}", message = "Mật khẩu phải chứa tối thiểu 8 ký tự")
	private String password;

	private String avatar;
}
