package vn.thangdev.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.thangdev.ecommerce.model.CartDetail;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
	Optional<CartDetail> findByCartIdAndProductId(Long cartId, Long productId);

	@NativeQuery("select count(id) from cart_details where cart_id = :cartId")
	int sumQuantityByCartId(@Param("cartId") Long cartId);

	@NativeQuery("SELECT cd.*, p.name, p.price, p.thumbnail FROM cart_details cd JOIN products p ON cd.product_id= p.id WHERE cd.cart_id=:cartId")
	List<CartDetail> findCartDetailsWithProduct(@Param("cartId") Long cartId);

	@Modifying
	@Query("DELETE FROM CartDetail cd WHERE cd IN :cartDetails")
	void deleteAllInBatchCustom(@Param("cartDetails") List<CartDetail> cartDetails);
}
