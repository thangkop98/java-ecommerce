package vn.thangdev.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.thangdev.ecommerce.model.CartDetail;
import vn.thangdev.ecommerce.repository.CartDetailRepository;

@Service
public class CartDetailService {
	private final CartDetailRepository cartDetailRepository;

	public CartDetailService(CartDetailRepository cartDetailRepository) {
		this.cartDetailRepository = cartDetailRepository;
	}

	public List<CartDetail> getCartDetails(Long cartId) {
		return cartDetailRepository.findCartDetailsWithProduct(cartId);
	}

	public void updateCartDetail(Long id, int quantity) {
		Optional<CartDetail> optionalCart = this.cartDetailRepository.findById(id);
		if (optionalCart.isPresent()) {
			CartDetail cart = optionalCart.get();
			cart.setQuantity(quantity);
			this.cartDetailRepository.save(cart);
		}
	}

	public void removeCartDetail(Long cartDetailId, Long userId) {
		CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
				.orElseThrow(() -> new RuntimeException("Cart detail không tồn tại"));
		if (!cartDetail.getCart().getUser().getId().equals(userId)) {
			throw new RuntimeException("Bạn không có quyền xóa sản phẩm này");
		}

		cartDetailRepository.delete(cartDetail);
	}
}
