package vn.thangdev.ecommerce.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.thangdev.ecommerce.dto.CreateUserDTO;
import vn.thangdev.ecommerce.dto.UpdateUserDTO;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.service.RoleService;
import vn.thangdev.ecommerce.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	private final UserService userService;
	private final RoleService roleService;

	public AdminUserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping
	public String getListUser(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Page<User> users;
		if (keyword == null || keyword.isBlank()) {
			users = this.userService.getListUser(page, size);
		} else {
			users = this.userService.searchListUser(keyword, page, size);
		}
		System.out.println("user list = " + users);
		model.addAttribute("users", users);
		model.addAttribute("keyword", keyword);
		return "/admin/user/list";
	}

	@GetMapping("/create")
	public String getCreateUser(Model model) {
		model.addAttribute("user", new CreateUserDTO());
		model.addAttribute("roles", this.roleService.getListRoles());
		return "/admin/user/create";
	}

	@PostMapping("/create")
	// binding phải đứng ngay sau object được validate
	public String postCreateUser(@Valid @ModelAttribute("user") CreateUserDTO inputUser, BindingResult bindingResult,
			@RequestParam("image") MultipartFile avatar, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("post create = " + inputUser);
		if (this.userService.checkExistedEmail(inputUser.getEmail())) {
			bindingResult.rejectValue("email", "email.exists", "Email đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", this.roleService.getListRoles());
			return "/admin/user/create";
		}
		this.userService.createUser(inputUser, avatar);
		redirectAttributes.addFlashAttribute("successMessage", "Tạo người dùng thành công!");
		return "redirect:/admin/users";
	}

	@GetMapping("/update/{id}")
	public String getUpdateUser(Model model, @PathVariable Long id) {
		User user = this.userService.findUserById(id)
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy user", "redirect:/admin/users"));

		model.addAttribute("user", user);
		model.addAttribute("roles", this.roleService.getListRoles());
		return "/admin/user/update";
	}

	@PostMapping("/update/{id}")
	public String postUpdateUser(@Valid @ModelAttribute("user") UpdateUserDTO inputUser, BindingResult bindingResult,
			@RequestParam(name = "image", required = false) MultipartFile avatar, Model model,
			RedirectAttributes redirectAttributes) {

		System.out.println("update user 1213" + inputUser);
		if (this.userService.checkExistedByEmailForUpdate(inputUser.getEmail(), inputUser.getId())) {
			bindingResult.rejectValue("email", "email.exists", "Email đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", this.roleService.getListRoles());
			return "/admin/user/update";
		}
		this.userService.updateUser(inputUser, avatar);
		redirectAttributes.addFlashAttribute("successMessage", "Cật nhật người dùng thành công!");
		return "redirect:/admin/users";
	}

	@GetMapping("/{id}")
	public String viewUser(Model model, @PathVariable Long id) {
		User user = this.userService.findUserById(id)
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy user", "redirect:/admin/users"));
		model.addAttribute("user", user);
		return "/admin/user/show";
	}

	@PostMapping("/delete/{id}")
	public String deleteUser(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
		User user = this.userService.findUserById(id)
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy user", "redirect:/admin/users"));
		this.userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng thành công!");
		return "redirect:/admin/users";
	}
}
