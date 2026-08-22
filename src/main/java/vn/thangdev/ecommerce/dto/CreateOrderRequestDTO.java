package vn.thangdev.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateOrderRequestDTO {
	@NotBlank(message = "Vui lòng điền họ tên")
	private String username;

	@NotBlank(message = "Vui lòng điền địa chỉ nhận hàng")
	private String address;

	@NotBlank(message = "Vui lòng điền số điện thoại")
	private String phone;

	public CreateOrderRequestDTO() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
