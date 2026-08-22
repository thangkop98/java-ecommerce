package vn.thangdev.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.thangdev.ecommerce.constant.RoleName;
import vn.thangdev.ecommerce.model.Role;
import vn.thangdev.ecommerce.repository.RoleRepository;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
				Role admin = new Role();
				admin.setName(RoleName.ADMIN);
				roleRepository.save(admin);
			}

			if (roleRepository.findByName(RoleName.USER).isEmpty()) {
				Role user = new Role();
				user.setName(RoleName.USER);
				roleRepository.save(user);
			}
		};
	}
}
