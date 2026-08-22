package vn.thangdev.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.thangdev.ecommerce.model.Product;
import vn.thangdev.ecommerce.model.ProductUseages;

@Repository
public interface ProductUseagesRepository extends JpaRepository<ProductUseages, Long> {
	void deleteAllByProduct(Product product);
}
