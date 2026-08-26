package vn.thangdev.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.constant.OrderStatus;
import vn.thangdev.ecommerce.dto.CreateOrderRequestDTO;
import vn.thangdev.ecommerce.model.CartDetail;
import vn.thangdev.ecommerce.model.Order;
import vn.thangdev.ecommerce.model.OrderDetail;
import vn.thangdev.ecommerce.model.Product;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.CartDetailRepository;
import vn.thangdev.ecommerce.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartDetailRepository cartDetailRepository;

	@Transactional
	public void createOrder(User user, List<CartDetail> cartDetails, CreateOrderRequestDTO data) {
		Order order = new Order();
		order.setUser(user);
		order.setCode("");
		order.setReceiverName(!data.getUsername().isBlank() ? data.getUsername() : user.getName());
		order.setReceiverPhone(!data.getPhone().isBlank() ? data.getPhone() : user.getPhone());
		order.setReceiverAddress(!data.getAddress().isBlank() ? data.getAddress() : user.getAddress());
		order.setStatus(OrderStatus.pending);

		BigDecimal total = BigDecimal.ZERO;

		for (CartDetail cartDetail : cartDetails) {

			Product product = cartDetail.getProduct();

			BigDecimal unitPrice = product.getPrice();

			BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(cartDetail.getQuantity()));

			OrderDetail orderDetail = new OrderDetail();

			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
			orderDetail.setProductName(product.getName());
			orderDetail.setQuantity(cartDetail.getQuantity());
			orderDetail.setUnitPrice(unitPrice);
			orderDetail.setSubtotal(subtotal);

			order.getOrderDetails().add(orderDetail);

			total = total.add(subtotal);
		}

		order.setTotalAmount(total);
		Order createdOrder = this.orderRepository.save(order);
		createdOrder.setCode("ord-" + createdOrder.getId().toString());
		this.orderRepository.save(createdOrder);
		this.cartDetailRepository.deleteAllInBatchCustom(cartDetails);
	}

	public Page<Order> getListOrder(Specification<Order> spec, int page, int size) {
		return this.orderRepository.findAll(spec,
				PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "createdAt")));
	}

	public Optional<Order> getDetailOrder(Long id) {
		return this.orderRepository.findById(id);
	}

	public void changeOrderStatus(Order order) {
		this.orderRepository.save(order);
	}

	public List<Order> findByUserEmail(String email) {
		return this.orderRepository.findByUserEmailOrderByCreatedAtDesc(email);
	}
}
