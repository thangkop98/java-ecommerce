package vn.thangdev.ecommerce.dto;

public class UpdateCartDetailRequestDTO {
	private Long cartDetailId;

	private int quantity;

	public UpdateCartDetailRequestDTO() {

	}

	public Long getCartDetailId() {
		return cartDetailId;
	}

	public void setCartDetailId(Long cartDetailId) {
		this.cartDetailId = cartDetailId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
