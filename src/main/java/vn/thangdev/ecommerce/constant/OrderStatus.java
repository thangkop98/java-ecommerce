package vn.thangdev.ecommerce.constant;

public enum OrderStatus {
	pending("Chờ xác nhận"), confirm("Đã xác nhận"), shipping("Đang giao hàng"), delivered("Đã giao hàng"),
	cancelled("Đã hủy");

	private final String displayName;

	OrderStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean canTransitionTo(OrderStatus next) {
		return switch (this) {
			case pending -> next == confirm || next == cancelled;
			case confirm -> next == shipping || next == cancelled;
			case shipping -> next == delivered;
			case delivered, cancelled -> false;
		};
	}
}
