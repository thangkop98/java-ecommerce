package vn.thangdev.ecommerce.controller.enduser;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Brand;
import vn.thangdev.ecommerce.model.Product;
import vn.thangdev.ecommerce.model.Useage;
import vn.thangdev.ecommerce.repository.BrandRepository;
import vn.thangdev.ecommerce.repository.UsageRepository;
import vn.thangdev.ecommerce.service.ProductService;

@Controller
public class ProductController {
	private final ProductService productService;
	private final BrandRepository brandRepository;
	private final UsageRepository usageRepository;

	public ProductController(ProductService productService, BrandRepository brandRepository,
			UsageRepository usageRepository) {
		this.productService = productService;
		this.brandRepository = brandRepository;
		this.usageRepository = usageRepository;
	}

	@GetMapping("/")
	public String homeListProduct(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size, Model model) {
		Page<Product> list = this.productService.getProductsInHomePage(page, size);
		model.addAttribute("products", list);

		return "/user/home";
	}

	@GetMapping("/product/{id}")
	public String detailProduct(Model model, @PathVariable Long id) {
		Product pr = this.productService.findProductById(id)
				.orElseThrow(() -> new ResourceNotFoundException(404, "Sản phẩm không tồn tại", "redirect:/"));

		List<Object[]> brands = this.productService.countProductsByBrand();
		model.addAttribute("product", pr);
		model.addAttribute("brands", brands);

		return "/user/product/detail";
	}

	@GetMapping("/products")
	public String listFilterProduct(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			@RequestParam(required = false) List<Long> brandIds, @RequestParam(required = false) List<Long> usageIds,
			@RequestParam(required = false) String price, @RequestParam(required = false) String sort, Model model) {

		Page<Product> products = this.productService.filterProducts(brandIds, usageIds, price, sort, page, size);
		List<Brand> brands = this.brandRepository.findAll();
		List<Useage> usages = this.usageRepository.findAll();

		model.addAttribute("products", products);
		model.addAttribute("brands", brands);
		model.addAttribute("usages", usages);

		return "/user/product/list";
	}
}
