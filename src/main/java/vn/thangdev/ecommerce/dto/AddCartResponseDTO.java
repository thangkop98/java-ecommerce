package vn.thangdev.ecommerce.dto;

public class AddCartResponseDTO {
	private boolean success;
	private int cartCount;
	private String message;

	public AddCartResponseDTO() {
	}

	public AddCartResponseDTO(boolean success, int cartCount, String message) {
		this.success = success;
		this.cartCount = cartCount;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getCartCount() {
		return cartCount;
	}

	public String getMessage() {
		return message;
	}
}
