package vn.thangdev.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
	private int code;
	private String message;
	private String redirectUrl;

	public ResourceNotFoundException(int code, String message, String redirectUrl) {
		this.code = code;
		this.message = message;
		this.redirectUrl = redirectUrl;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
}
