package vn.thangdev.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
