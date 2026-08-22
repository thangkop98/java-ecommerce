package vn.thangdev.ecommerce.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.thangdev.ecommerce.constant.RoleName;
import vn.thangdev.ecommerce.dto.RegisterUserDTO;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Role;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.RoleRepository;
import vn.thangdev.ecommerce.repository.UserRepository;

@Controller
public class AuthController {
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final UserRepository userRepository;

	AuthController(RoleRepository roleRepository, PasswordEncoder encoder, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String login() {
		return "/auth/login";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("user", new RegisterUserDTO());
		return "/auth/register";
	}

	@PostMapping("/register")
	public String postRegister(@Valid @ModelAttribute("user") RegisterUserDTO inputUser, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		Role userRole = this.roleRepository.findByName(RoleName.USER)
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy role", "redirect:/register"));

		if (!inputUser.getPassword().equals(inputUser.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "password.mismatch", "Mật khẩu xác nhận không khớp");
		}

		if (bindingResult.hasErrors()) {
			return "/auth/register";
		}

		User user = new User();
		user.setName(inputUser.getName());
		user.setEmail(inputUser.getEmail());
		user.setPassword(this.encoder.encode(inputUser.getPassword()));
		user.setPhone(inputUser.getPhone());
		user.setAddress(inputUser.getAddress());
		user.setRole_id(userRole.getId());

		this.userRepository.save(user);
		redirectAttributes.addFlashAttribute("successMessage", "Đăng ký người dùng thành công");
		return "redirect:/login";
	}

	@GetMapping("/403")
	public String forbidden() {
		return "/error/403";
	}
}
