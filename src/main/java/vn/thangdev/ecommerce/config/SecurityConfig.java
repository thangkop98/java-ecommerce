package vn.thangdev.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomAuthenticationSuccessHandler successHandler;

	// Port riêng của actuator (health, metrics). Không publish ra ngoài nên cho phép truy cập tự do,
	// còn port web 8080 vẫn giữ nguyên rule bảo mật bên dưới.
	@Value("${management.server.port:-1}")
	private int managementPort;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(request -> request.getLocalPort() == managementPort).permitAll()
						.requestMatchers("/", "/login", "/error", "/register", "/css/**", "/js/**", "/images/**",
								"/uploads/**", "/cart/add", "/products", "/product/**")
						.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").successHandler(successHandler).failureUrl("/login?error")
						.permitAll())
				.sessionManagement((session) -> session.maximumSessions(1).maxSessionsPreventsLogin(false)
						.expiredUrl("/login?expired"))
				.logout(logout -> logout.deleteCookies("JSESSIONID").logoutSuccessUrl("/"))
				.rememberMe((rememberMe) -> rememberMe.rememberMeServices(rememberMeServices()))
				.exceptionHandling((exception) -> exception.accessDeniedPage("/403"));

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SpringSessionRememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();

		rememberMeServices.setAlwaysRemember(false);
		rememberMeServices.setValiditySeconds(30 * 24 * 60 * 60);
		return rememberMeServices;
	}
}
