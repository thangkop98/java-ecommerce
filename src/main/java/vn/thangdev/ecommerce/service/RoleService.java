package vn.thangdev.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.model.Role;
import vn.thangdev.ecommerce.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public List<Role> getListRoles() {
		return this.roleRepository.findAll();
	}
}
