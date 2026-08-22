package vn.thangdev.ecommerce.dto;

public class AddCartRequestDTO {

	private Long productId;

	private int quantity;

	public AddCartRequestDTO() {

	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
