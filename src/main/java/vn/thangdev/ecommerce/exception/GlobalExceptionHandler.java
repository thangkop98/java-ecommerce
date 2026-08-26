package vn.thangdev.ecommerce.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFound(ResourceNotFoundException ex, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

		return ex.getRedirectUrl();
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public String handleResourceNotFound(NoResourceFoundException ex) {
		return "redirect:/not-found";
	}
}
