package vn.thangdev.ecommerce.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final CustomAuthenticationSuccessHandler successHandler;

	public SecurityConfig(CustomAuthenticationSuccessHandler successHandler) {
		this.successHandler = successHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize
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

//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//		UserDetails userDetails = User.withUsername("thangdz").password(passwordEncoder.encode("123456")).roles("USER")
//				.build();
//		System.out.println("user details = " + userDetails);
//		return new InMemoryUserDetailsManager(userDetails);
//	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SpringSessionRememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();

		rememberMeServices.setAlwaysRemember(false);
		rememberMeServices.setValiditySeconds(30 * 24 * 60 * 60);
		return rememberMeServices;
	}
}
