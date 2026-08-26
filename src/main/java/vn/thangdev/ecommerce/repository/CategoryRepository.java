package vn.thangdev.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.thangdev.ecommerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Page<Category> findAll(Pageable pageable);

	@Query("select c from Category c where c.name like %:keyword%")
	Page<Category> search(@Param("keyword") String keyword, Pageable pageable);

	boolean existsByNameAndIdNot(String name, Long id);

	boolean existsByName(String name);
}
