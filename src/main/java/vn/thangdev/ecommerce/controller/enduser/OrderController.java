package vn.thangdev.ecommerce.controller.enduser;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.thangdev.ecommerce.dto.CreateOrderRequestDTO;
import vn.thangdev.ecommerce.model.Cart;
import vn.thangdev.ecommerce.model.CartDetail;
import vn.thangdev.ecommerce.model.Order;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.UserRepository;
import vn.thangdev.ecommerce.service.CartService;
import vn.thangdev.ecommerce.service.OrderService;

@Controller
public class OrderController {
	private final OrderService orderService;
	private final CartService cartService;
	private final UserRepository userRepository;

	public OrderController(OrderService orderService, CartService cartService, UserRepository userRepository) {
		this.orderService = orderService;
		this.cartService = cartService;
		this.userRepository = userRepository;
	}

	@GetMapping("/thanks")
	public String thanks() {
		return "user/thanks";
	}

	@PostMapping("/order/create")
	public String createOrder(Authentication authentication, @ModelAttribute("user") CreateOrderRequestDTO data,
			Model model) {
		User user = userRepository.findUserByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User không tồn tại"));

		Cart cart = user.getCart();
		List<CartDetail> cartDetails = cart.getCartDetails();

		this.orderService.createOrder(user, cartDetails, data);
		return "redirect:/thanks";
	}

	@GetMapping("/order-history")
	public String orderHistory(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		List<Order> orders = orderService.findByUserEmail(userDetails.getUsername());
		model.addAttribute("orders", orders);
		return "user/order-history";
	}
}
