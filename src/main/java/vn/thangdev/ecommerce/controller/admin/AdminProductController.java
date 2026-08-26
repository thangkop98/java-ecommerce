package vn.thangdev.ecommerce.controller.admin;

import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.thangdev.ecommerce.dto.CreateProductDTO;
import vn.thangdev.ecommerce.dto.UpdateProductDTO;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Product;
import vn.thangdev.ecommerce.repository.BrandRepository;
import vn.thangdev.ecommerce.repository.CategoryRepository;
import vn.thangdev.ecommerce.repository.UsageRepository;
import vn.thangdev.ecommerce.service.ProductService;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {
	private final ProductService productService;
	private final CategoryRepository categoryRepository;
	private final BrandRepository brandRepository;
	private final UsageRepository usageRepository;

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Id không đúng định dạng, vui lòng kiểm tra lại!");
		return "redirect:/admin/product";
	}

	@GetMapping
	public String list(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {
		Page<Product> list = this.productService.getListProduct(keyword, page, size);
		model.addAttribute("products", list);
		model.addAttribute("keyword", keyword);
		return "/admin/product/list";
	}

	@GetMapping("/create")
	public String getCreateProduct(Model model) {
		model.addAttribute("product", new CreateProductDTO());
		model.addAttribute("categories", this.categoryRepository.findAll());
		model.addAttribute("brands", this.brandRepository.findAll());
		model.addAttribute("usages", this.usageRepository.findAll());
		return "/admin/product/create";
	}

	@PostMapping("/create")
	public String postCreateProduct(@Valid @ModelAttribute("product") CreateProductDTO data,
			@RequestParam("thumbnail") MultipartFile thumbnail, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "/admin/product/create";
		}
		this.productService.createProduct(data, thumbnail);
		redirectAttributes.addFlashAttribute("successMessage", "Tạo sản phẩm thành công!");
		return "redirect:/admin/product";
	}

	@GetMapping("/update/{id}")
	public String getUpdateProduct(Model model, @PathVariable Long id) {
		Product product = this.productService.findProductById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy sản phẩm", "redirect:/admin/product"));

		UpdateProductDTO dto = new UpdateProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setPrice(product.getPrice());
		dto.setStockQuantity(product.getStock_quantity());
		dto.setCategoryId(product.getCategory().getId());
		dto.setBrandId(product.getBrand().getId());
		dto.setDescription(product.getDescription());
		dto.setStatus(product.getStatus());
		dto.setUsageIds(product.getProductUsages().stream().map(productUsage -> productUsage.getUsage().getId())
				.collect(Collectors.toSet()));

		dto.setThumbnail(product.getThumbnail());
		model.addAttribute("product", dto);
		model.addAttribute("categories", this.categoryRepository.findAll());
		model.addAttribute("brands", this.brandRepository.findAll());
		model.addAttribute("usages", this.usageRepository.findAll());

		return "/admin/product/update";
	}

	@PostMapping("/update/{id}")
	public String postUpdateProduct(@Valid @ModelAttribute("product") UpdateProductDTO data,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile thumbnail) {

		if (bindingResult.hasErrors()) {
			return "/admin/product/update";
		}
		this.productService.updateProduct(data, thumbnail);
		redirectAttributes.addFlashAttribute("successMessage", "Cật nhật sản phẩm thành công!");
		return "redirect:/admin/product";
	}

	@PostMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Product product = this.productService.findProductById(id).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy sản phẩm", "redirect:/admin/product"));
		this.productService.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công");
		return "redirect:/admin/product";
	}
}
