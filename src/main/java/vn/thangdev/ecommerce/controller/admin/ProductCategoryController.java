package vn.thangdev.ecommerce.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Category;
import vn.thangdev.ecommerce.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class ProductCategoryController {
	private final CategoryService categoryService;

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
		RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Id không đúng định dạng, vui lòng kiểm tra lại!");
		return "redirect:/admin/category";
	}

	@GetMapping
	public String list(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {
		Page<Category> list = this.categoryService.getListCategory(keyword, page, size);
		model.addAttribute("categories", list);
		return "/admin/category/list";
	}

	@GetMapping("/create")
	public String getCreateCategory(Model model) {
		model.addAttribute("category", new Category());
		return "/admin/category/create";
	}

	@PostMapping("/create")
	public String postCreateCategory(@Valid @ModelAttribute("category") Category data, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {

		if (this.categoryService.checkExistedName(data.getName())) {
			bindingResult.rejectValue("name", "name.exists", "Tên danh mục đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			return "/admin/category/create";
		}
		this.categoryService.createCategory(data);
		redirectAttributes.addFlashAttribute("successMessage", "Tạo danh mục thành công!");
		return "redirect:/admin/category";
	}

	@GetMapping("/update/{id}")
	public String getUpdateCategory(Model model, @PathVariable Long id) {
		Category category = this.categoryService.findCategoryById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy danh mục", "redirect:/admin/category"));

		model.addAttribute("category", category);
		return "/admin/category/update";
	}

	@PostMapping("/update/{id}")
	public String postUpdateCategory(@Valid @ModelAttribute("category") Category data, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		if (this.categoryService.checkExistedCategory(data.getName(), data.getId())) {
			bindingResult.rejectValue("name", "name.exists", "Tên danh mục đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			return "/admin/category/update";
		}
		this.categoryService.updateCategory(data);
		redirectAttributes.addFlashAttribute("successMessage", "Cật nhật danh mục thành công!");
		return "redirect:/admin/category";
	}

	@PostMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Category category = this.categoryService.findCategoryById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy danh mục", "redirect:/admin/category"));
		this.categoryService.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục thành công");
		return "redirect:/admin/category";
	}

}
