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
import vn.thangdev.ecommerce.model.Brand;
import vn.thangdev.ecommerce.service.BrandService;

@Controller
@RequestMapping("/admin/brand")
@RequiredArgsConstructor
public class BrandController {
	private final BrandService brandService;

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Id không đúng định dạng, vui lòng kiểm tra lại!");
		return "redirect:/admin/brand";
	}

	@GetMapping
	public String list(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {
		Page<Brand> list = this.brandService.getListBrand(keyword, page, size);
		model.addAttribute("brands", list);
		return "admin/brand/list";
	}

	@GetMapping("/create")
	public String getCreateBrand(Model model) {
		model.addAttribute("brand", new Brand());
		return "admin/brand/create";
	}

	@PostMapping("/create")
	public String postCreateBrand(@Valid @ModelAttribute("brand") Brand data, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (this.brandService.checkExistedName(data.getName())) {
			bindingResult.rejectValue("name", "name.exists", "Tên hãng sản xuất đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			return "admin/brand/create";
		}
		this.brandService.createBrand(data);
		redirectAttributes.addFlashAttribute("successMessage", "Tạo hãng sản xuất thành công!");
		return "redirect:/admin/brand";
	}

	@GetMapping("/update/{id}")
	public String getUpdateBrand(Model model, @PathVariable Long id) {
		Brand brand = this.brandService.findBrandById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy hãng sản xuất", "redirect:/admin/brand"));

		model.addAttribute("brand", brand);
		return "admin/brand/update";
	}

	@PostMapping("/update/{id}")
	public String postUpdateBrand(@Valid @ModelAttribute("brand") Brand data, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (this.brandService.checkExistedBrand(data.getName(), data.getId())) {
			bindingResult.rejectValue("name", "name.exists", "Tên hãng sản xuất đã tồn tại");
		}

		if (bindingResult.hasErrors()) {
			return "admin/brand/update";
		}
		this.brandService.updateBrand(data);
		redirectAttributes.addFlashAttribute("successMessage", "Cật nhật hãng sản xuất thành công!");
		return "redirect:/admin/brand";
	}

	@PostMapping("/delete/{id}")
	public String deleteBrand(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Brand brand = this.brandService.findBrandById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy hãng sản xuất", "redirect:/admin/brand"));
		this.brandService.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Xóa hãng sản xuất thành công");
		return "redirect:/admin/brand";
	}
}
