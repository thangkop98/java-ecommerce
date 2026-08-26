package vn.thangdev.ecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.CartDetailRepository;
import vn.thangdev.ecommerce.repository.UserRepository;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalController {
	private final UserRepository userRepository;
	private final CartDetailRepository cartDetailRepository;

	@ModelAttribute
	public void addCurrentUser(Model model, Authentication authentication) {

		if (authentication != null && authentication.isAuthenticated()) {
			User user = this.userRepository.findUserByEmail(authentication.getName()).orElse(null);

			model.addAttribute("currentUser", user);
		}
	}

	@ModelAttribute
	public void addCartCount(Model model, Authentication authentication) {
		int cartCount = 0;

		if (authentication != null && authentication.isAuthenticated()) {
			User user = this.userRepository.findUserByEmail(authentication.getName()).orElse(null);
			if (user.getCart() != null) {
				cartCount = cartDetailRepository.sumQuantityByCartId(user.getCart().getId());
			}
			model.addAttribute("cartCount", cartCount);
		}
	}
}
