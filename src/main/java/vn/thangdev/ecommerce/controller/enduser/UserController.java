package vn.thangdev.ecommerce.controller.enduser;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.dto.UpdateAccountDTO;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/manage-user")
	public String manageAccount(Authentication auth, Model model) {
		User user = this.userService.findUserByEmail(auth.getName())
				.orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

		model.addAttribute("user", user);
		return "user/manage";
	}

	@PostMapping("/manage-user")
	public String updateAccount(Authentication auth, Model model,
			@Valid @ModelAttribute("user") UpdateAccountDTO inputUser, BindingResult bindingResult,
			@RequestParam(name = "image", required = false) MultipartFile avatar,
			RedirectAttributes redirectAttributes) {
		if (this.userService.checkExistedByEmailForUpdate(inputUser.getEmail(), inputUser.getId())) {
			bindingResult.rejectValue("email", "email.exists", "Email đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			return "user/manage";
		}
		this.userService.updateAccount(inputUser, avatar);
		redirectAttributes.addFlashAttribute("updateAccountSuccess", "Cật nhật tài khoản thành công!");
		return "redirect:/manage-user";
	}
}
