package vn.thangdev.ecommerce.controller.admin;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.constant.OrderStatus;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Order;
import vn.thangdev.ecommerce.model.OrderDetail;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.UserRepository;
import vn.thangdev.ecommerce.service.OrderService;
import vn.thangdev.ecommerce.specification.OrderSpec;

@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
	private final OrderService orderService;
	private final UserRepository userRepository;

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Id không đúng định dạng, vui lòng kiểm tra lại!");
		return "redirect:/admin/order";
	}

	@GetMapping
	public String listOrder(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			@RequestParam(required = false, name = "code") String code,
			@RequestParam(required = false, name = "name") String name,
			@RequestParam(required = false, name = "status") String status,
			@RequestParam(required = false, name = "phone") String phone,
			@RequestParam(required = false, name = "fromDate") String fromDate,
			@RequestParam(required = false, name = "toDate") String toDate, Model model) {

		Specification<Order> spec = OrderSpec.filterOrders(code, name, status, phone, fromDate, toDate);
		Page<Order> orders = this.orderService.getListOrder(spec, page, size);

		model.addAttribute("orders", orders);
		model.addAttribute("statuses", OrderStatus.values());
		model.addAttribute("code", code);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("status", status);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);

		return "admin/order/list";
	}

	@GetMapping("/view/{id}")
	public String detailOrder(@PathVariable Long id, Authentication auth, Model model) {
		Order order = this.orderService.getDetailOrder(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy đơn hàng", "redirect:/admin/order"));

		User user = this.userRepository.findById(order.getUser().getId())
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy thông tin người đặt hàng",
						"redirect:/admin/order"));

		BigDecimal total = order.getOrderDetails().stream().map(OrderDetail::getSubtotal).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		model.addAttribute("order", order);
		model.addAttribute("user", user);
		model.addAttribute("orderdetails", order.getOrderDetails());
		model.addAttribute("total", total);
		model.addAttribute("statuses", OrderStatus.values());

		return "admin/order/update";
	}

	@PostMapping("/update/{id}")
	public String updateOrder(@PathVariable Long id, @ModelAttribute(name = "status") OrderStatus newStatus,
			RedirectAttributes redirect) {
		Order order = this.orderService.getDetailOrder(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy đơn hàng", "redirect:/admin/order"));
		if (!order.getStatus().equals(newStatus) &&!order.getStatus().canTransitionTo(newStatus)) {
			redirect.addFlashAttribute("errorMessage", "Không thể chuyển trạng thái từ "
					+ order.getStatus().getDisplayName() + " thành " + newStatus.getDisplayName());
			return "redirect:/admin/order/view/" + id;
		}

		order.setStatus(newStatus);
		orderService.changeOrderStatus(order);
		redirect.addFlashAttribute("successMessage", "Cập nhật đơn hàng thành công");

		return "redirect:/admin/order";
	}
}
