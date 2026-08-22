package vn.thangdev.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.thangdev.ecommerce.constant.RoleName;
import vn.thangdev.ecommerce.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}
