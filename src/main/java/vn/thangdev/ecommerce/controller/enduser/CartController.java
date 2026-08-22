package vn.thangdev.ecommerce.controller.enduser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.thangdev.ecommerce.dto.AddCartRequestDTO;
import vn.thangdev.ecommerce.dto.CreateOrderRequestDTO;
import vn.thangdev.ecommerce.dto.UpdateCartDetailRequestDTO;
import vn.thangdev.ecommerce.model.CartDetail;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.UserRepository;
import vn.thangdev.ecommerce.service.CartDetailService;
import vn.thangdev.ecommerce.service.CartService;

@Controller
public class CartController {
	private final CartService cartService;
	private final CartDetailService cartDetailService;
	private final UserRepository userRepository;

	public CartController(CartService cartService, UserRepository userRepository, CartDetailService cartDetailService) {
		this.cartService = cartService;
		this.cartDetailService = cartDetailService;
		this.userRepository = userRepository;
	}

	@GetMapping("/cart")
	public String cartDetails(Authentication authentication, Model model) {
		User user = userRepository.findUserByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User không tồn tại"));
		if (user.getCart() != null) {
			List<CartDetail> cartDetails = this.cartDetailService.getCartDetails(user.getCart().getId());
			model.addAttribute("cartdetails", cartDetails);
		}

		return "/user/cart/list";
	}

	@PostMapping("/cart/add")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addToCart(@RequestBody AddCartRequestDTO request,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("success", false, "message", "Bạn cần đăng nhập tài khoản"));
		}

		User user = userRepository.findUserByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User không tồn tại"));

		int cartCount = cartService.addToCart(user, request.getProductId(), request.getQuantity());

		return ResponseEntity
				.ok(Map.of("success", true, "cartCount", cartCount, "message", "Thêm vào giỏ hàng thành công"));
	}

	@PostMapping("/cart/update")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateCartDetail(@RequestBody UpdateCartDetailRequestDTO request,
			Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("success", false, "message", "Bạn cần đăng nhập tài khoản"));
		}

		User user = userRepository.findUserByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User không tồn tại"));

		cartDetailService.updateCartDetail(request.getCartDetailId(), request.getQuantity());

		return ResponseEntity.ok(Map.of("success", true, "message", "Cập nhật giỏ hàng thành công"));
	}

	@DeleteMapping("/cart/remove/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteCartDetail(@PathVariable Long id, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("success", false, "message", "Bạn cần đăng nhập tài khoản"));
		}

		User user = userRepository.findUserByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User không tồn tại"));

		cartDetailService.removeCartDetail(id, user.getId());

		return ResponseEntity.ok(Map.of("success", true, "message", "Xóa sản phẩm khỏi giỏ hàng thành công"));
	}

	@GetMapping("/checkout")
	public String checkout(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
		User user = userRepository.findUserByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User không tồn tại"));
		List<CartDetail> cartDetails = this.cartDetailService.getCartDetails(user.getCart().getId());
		if (cartDetails.isEmpty()) {
			redirectAttributes.addFlashAttribute("noCartDetailMessage", "Không có sản phẩm nào trong giỏ hàng");
			return "redirect:/cart";
		}
		BigDecimal total = BigDecimal.ZERO;

		for (CartDetail cartDetail : cartDetails) {

			BigDecimal price = cartDetail.getProduct().getPrice();

			int quantity = cartDetail.getQuantity();

			BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));

			total = total.add(subtotal);
		}

		model.addAttribute("cartdetails", cartDetails);
		model.addAttribute("total", total);
		model.addAttribute("user", new CreateOrderRequestDTO());

		return "/user/cart/checkout";
	}
}
