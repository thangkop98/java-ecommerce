package vn.thangdev.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.repository.OrderRepository;
import vn.thangdev.ecommerce.repository.ProductRepository;
import vn.thangdev.ecommerce.repository.UserRepository;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@GetMapping
	public String dashboard(Model model) {
		Long countOrder = this.orderRepository.count();
		Long countProduct = this.productRepository.count();
		Long countUser = this.userRepository.count();

		model.addAttribute("order", countOrder);
		model.addAttribute("product", countProduct);
		model.addAttribute("user", countUser);

		return "/admin/dashboard";
	}
}
