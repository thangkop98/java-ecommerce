package vn.thangdev.ecommerce.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User userFromDB = this.userRepository.findUserByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<SimpleGrantedAuthority> authorities = List
				.of(new SimpleGrantedAuthority("ROLE_" + userFromDB.getRole().getName()));

		UserDetails user = new org.springframework.security.core.userdetails.User(userFromDB.getEmail(),
				userFromDB.getPassword(), authorities);
		return user;

	}
}
