package vn.thangdev.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.thangdev.ecommerce.constant.CommonStatus;
import vn.thangdev.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	Page<Product> findAll(Pageable pageable);

	@Query("select c from Product c where c.name like %:keyword%")
	Page<Product> search(@Param("keyword") String keyword, Pageable pageable);

	Page<Product> findByStatus(CommonStatus status, Pageable pagable);

	@Query("SELECT c.id, c.name, count(p.id) from Category c left join Product p on p.category.id = c.id group by c.id, c.name")
	List<Object[]> countProductsByCategory();

	@Query("SELECT c.id, c.name, count(p.id) from Brand c left join Product p on p.brand.id = c.id group by c.id, c.name")
	List<Object[]> countProductsByBrand();
}
