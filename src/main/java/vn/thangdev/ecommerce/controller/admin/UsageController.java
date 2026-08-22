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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Useage;
import vn.thangdev.ecommerce.service.UsageService;

@Controller
@RequestMapping("/admin/usage")
public class UsageController {
	private final UsageService usageService;

	public UsageController(UsageService usageService) {
		this.usageService = usageService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {
		Page<Useage> list = this.usageService.getListUsage(keyword, page, size);
		model.addAttribute("usages", list);
		return "/admin/usage/list";
	}

	@GetMapping("/create")
	public String getCreateUsage(Model model) {
		model.addAttribute("usage", new Useage());
		return "/admin/usage/create";
	}

	@PostMapping("/create")
	public String postCreateUsage(@Valid @ModelAttribute("usage") Useage data, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "/admin/usage/create";
		}
		this.usageService.createUsage(data);
		redirectAttributes.addFlashAttribute("successMessage", "Tạo mục đích sử dụng thành công!");
		return "redirect:/admin/usage";
	}

	@GetMapping("/update/{id}")
	public String getUpdateUsage(Model model, @PathVariable Long id) {
		Useage useage = this.usageService.findUsageById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy mục đích sử dụng", "redirect:/admin/usage"));

		model.addAttribute("usage", useage);
		return "/admin/usage/update";
	}

	@PostMapping("/update/{id}")
	public String postUpdateUsage(@Valid @ModelAttribute("usage") Useage data, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (this.usageService.checkExistedUsage(data.getName(), data.getId())) {
			bindingResult.rejectValue("name", "name.exists", "Tên mục đích sử dụng đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			return "/admin/usage/update";
		}
		this.usageService.updateUsage(data);
		redirectAttributes.addFlashAttribute("successMessage", "Cật nhật mục đích sử dụng thành công!");
		return "redirect:/admin/usage";
	}

	@PostMapping("/delete/{id}")
	public String deleteUsage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Useage usage = this.usageService.findUsageById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy mục đích sử dụng", "redirect:/admin/usage"));
		this.usageService.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Xóa mục đích sử dụng thành công");
		return "redirect:/admin/usage";
	}
}
