package vn.thangdev.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.thangdev.ecommerce.model.Cart;
import vn.thangdev.ecommerce.model.CartDetail;
import vn.thangdev.ecommerce.model.Product;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.CartDetailRepository;
import vn.thangdev.ecommerce.repository.CartRepository;
import vn.thangdev.ecommerce.repository.ProductRepository;

@Service
public class CartService {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final CartDetailRepository cartDetailRepository;

	public CartService(CartRepository cartRepository, ProductRepository productRepository,
			CartDetailRepository cartDetailRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.cartDetailRepository = cartDetailRepository;
	}

	@Transactional
	public int addToCart(User user, Long productId, int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0");
		}

		Optional<Cart> optionalCart = this.cartRepository.findByUserId(user.getId());

		Cart cart;
		if (optionalCart.isPresent()) {
			cart = optionalCart.get();
		} else {
			cart = new Cart();
			cart.setUser(user);
			this.cartRepository.save(cart);
		}

		Product product = this.productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product không tồn tại"));

		Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId(),
				productId);
		;

		if (optionalCartDetail.isPresent()) {
			CartDetail cartDetail = optionalCartDetail.get();
			cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
			this.cartDetailRepository.save(cartDetail);
		} else {
			CartDetail cartDetail = new CartDetail();
			cartDetail.setCart(cart);
			cartDetail.setProduct(product);
			cartDetail.setQuantity(quantity);
			this.cartDetailRepository.save(cartDetail);
		}

		return cartDetailRepository.sumQuantityByCartId(cart.getId());
	}
}
