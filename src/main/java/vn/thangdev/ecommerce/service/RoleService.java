package vn.thangdev.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.thangdev.ecommerce.model.Role;
import vn.thangdev.ecommerce.repository.RoleRepository;

@Service
public class RoleService {
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<Role> getListRoles() {
		return this.roleRepository.findAll();
	}
}
