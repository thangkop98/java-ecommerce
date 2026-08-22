package vn.thangdev.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.thangdev.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, Long id);

	Page<User> findAll(Pageable pageable);

	@Query("select u from User u where u.name like %:keyword% or u.email like %:keyword% or u.phone like %:keyword%")
	Page<User> search(@Param("keyword") String keyword, Pageable pageable);
}
