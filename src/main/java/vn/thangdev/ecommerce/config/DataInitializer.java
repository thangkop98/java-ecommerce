package vn.thangdev.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.thangdev.ecommerce.constant.RoleName;
import vn.thangdev.ecommerce.model.Role;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.RoleRepository;
import vn.thangdev.ecommerce.repository.UserRepository;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			Role adminRole = roleRepository.findByName(RoleName.ADMIN)
					.orElseGet(() -> {
						Role role = new Role();
						role.setName(RoleName.ADMIN);
						return roleRepository.save(role);
					});

			if (userRepository.findUserByEmail("admin@gmail.com").isEmpty()) {
				User adminUser = new User();
				adminUser.setEmail("admin@gmail.com");
				adminUser.setPassword(passwordEncoder.encode("12345678"));
				adminUser.setRole(adminRole);
				adminUser.setPhone("0987123455");
				adminUser.setName("admin");

				userRepository.save(adminUser);
			}

			Role userRole = roleRepository.findByName(RoleName.USER)
					.orElseGet(() -> {
						Role role = new Role();
						role.setName(RoleName.USER);
						return roleRepository.save(role);
					});

			if (userRepository.findUserByEmail("user@gmail.com").isEmpty()) {
				User userCustomer = new User();
				userCustomer.setEmail("user@gmail.com");
				userCustomer.setPassword(passwordEncoder.encode("12345678"));
				userCustomer.setRole(userRole);
				userCustomer.setPhone("0987123456");
				userCustomer.setName("user");

				userRepository.save(userCustomer);
			}
		};
	}
}
