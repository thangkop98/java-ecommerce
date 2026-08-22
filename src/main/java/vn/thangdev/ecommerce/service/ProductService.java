package vn.thangdev.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import vn.thangdev.ecommerce.constant.CommonStatus;
import vn.thangdev.ecommerce.dto.CreateProductDTO;
import vn.thangdev.ecommerce.dto.UpdateProductDTO;
import vn.thangdev.ecommerce.exception.ResourceNotFoundException;
import vn.thangdev.ecommerce.model.Brand;
import vn.thangdev.ecommerce.model.Category;
import vn.thangdev.ecommerce.model.Product;
import vn.thangdev.ecommerce.model.ProductUseages;
import vn.thangdev.ecommerce.model.Useage;
import vn.thangdev.ecommerce.repository.BrandRepository;
import vn.thangdev.ecommerce.repository.CategoryRepository;
import vn.thangdev.ecommerce.repository.ProductRepository;
import vn.thangdev.ecommerce.repository.ProductUseagesRepository;
import vn.thangdev.ecommerce.repository.UsageRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final BrandRepository brandRepository;
	private final CategoryRepository categoryRepository;
	private final UsageRepository usageRepository;
	private final ProductUseagesRepository productUseagesRepository;
	private final FileUploadService fileUploadService;

	@Value("${app.product-upload-dir-absolute}")
	private String uploadDirAbsolute;

	@Value("${app.product-upload-dir-relative}")
	private String uploadDirRelative;

	public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
			CategoryRepository categoryRepository, UsageRepository usageRepository,
			ProductUseagesRepository productUseagesRepository, FileUploadService fileUploadService) {
		this.productRepository = productRepository;
		this.brandRepository = brandRepository;
		this.categoryRepository = categoryRepository;
		this.usageRepository = usageRepository;
		this.productUseagesRepository = productUseagesRepository;
		this.fileUploadService = fileUploadService;
	}

	public Page<Product> getListProduct(String keyword, int page, int size) {
		Page<Product> list;
		Pageable pageable = PageRequest.of(page, size);
		if (keyword != null) {
			list = this.productRepository.search(keyword, pageable);
		} else {
			list = this.productRepository.findAll(pageable);
		}

		return list;
	}

	@Transactional
	public void createProduct(CreateProductDTO data, MultipartFile thumbnail) {
		Brand brand = this.brandRepository.findById(data.getBrandId())
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy hãng sản xuất",
						"redirect:/admin/product/create"));

		Category category = this.categoryRepository.findById(data.getCategoryId()).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy danh mục", "redirect:/admin/product/create"));
		try {
			Product pr = new Product();
			pr.setName(data.getName());
			pr.setDescription(data.getDescription());
			pr.setPrice(data.getPrice());
			pr.setStock_quantity(data.getStockQuantity());
			pr.setBrand(brand);
			pr.setCategory(category);
			pr.setStatus(data.getStatus());
			pr.setSlug(pr.generateSlug(data.getName()));
			pr.setSku(pr.generateSku());

			if (thumbnail != null && !thumbnail.isEmpty()) {
				String path = this.fileUploadService.upload(thumbnail, this.uploadDirAbsolute, this.uploadDirRelative,
						"product");
				if (!path.isEmpty()) {
					pr.setThumbnail(path);
				}
			}

			this.productRepository.save(pr);

			if (data.getUsageIds() != null && !data.getUsageIds().isEmpty()) {

				List<Useage> usages = this.usageRepository.findAllById(data.getUsageIds());

				List<ProductUseages> productUsages = usages.stream().map(usage -> {
					ProductUseages productUsage = new ProductUseages();
					productUsage.setProduct(pr);
					productUsage.setUsage(usage);
					return productUsage;
				}).toList();

				this.productUseagesRepository.saveAll(productUsages);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi tạo sản phẩm" + e);
			throw e;
		}
	}

	public Optional<Product> findProductById(Long id) {
		return this.productRepository.findById(id);
	}

	@Transactional
	public void updateProduct(UpdateProductDTO data, MultipartFile thumbnail) {
		Product pr = this.productRepository.findById(data.getId()).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy sản phẩm", "redirect:/admin/product/create"));
		Brand brand = this.brandRepository.findById(data.getBrandId())
				.orElseThrow(() -> new ResourceNotFoundException(404, "Không tìm thấy hãng sản xuất",
						"redirect:/admin/product/create"));

		Category category = this.categoryRepository.findById(data.getCategoryId()).orElseThrow(
				() -> new ResourceNotFoundException(404, "Không tìm thấy danh mục", "redirect:/admin/product/create"));
		try {
			pr.setName(data.getName());
			pr.setDescription(data.getDescription());
			pr.setPrice(data.getPrice());
			pr.setStock_quantity(data.getStockQuantity());
			pr.setBrand(brand);
			pr.setCategory(category);
			pr.setStatus(data.getStatus());

			if (thumbnail != null && !thumbnail.isEmpty()) {
				String path = this.fileUploadService.upload(thumbnail, this.uploadDirAbsolute, this.uploadDirRelative,
						"product");
				if (!path.isEmpty()) {
					pr.setThumbnail(path);
				}
			}

			this.productRepository.save(pr);
			this.productUseagesRepository.deleteAllByProduct(pr);
			this.productUseagesRepository.flush();

			if (data.getUsageIds() != null && !data.getUsageIds().isEmpty()) {

				List<Useage> usages = this.usageRepository.findAllById(data.getUsageIds());

				List<ProductUseages> productUsages = usages.stream().map(usage -> {
					ProductUseages productUsage = new ProductUseages();
					productUsage.setProduct(pr);
					productUsage.setUsage(usage);
					return productUsage;
				}).toList();

				this.productUseagesRepository.saveAll(productUsages);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi cập nhật sản phẩm" + e);
			throw e;
		}
	}

	public void deleteById(Long id) {
		this.productRepository.deleteById(id);
	}

	public Page<Product> getProductsInHomePage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return this.productRepository.findByStatus(CommonStatus.active, pageable);
	}

	public List<Object[]> countProductsByCategory() {
		return this.productRepository.countProductsByCategory();
	}

	public List<Object[]> countProductsByBrand() {
		return this.productRepository.countProductsByBrand();
	}

	public Page<Product> filterProducts(List<Long> brandIds, List<Long> usageIds, String price, String sort, int page,
			int size) {

		Specification<Product> spec = this.filter(brandIds, usageIds, price);

		Sort sorting = Sort.unsorted();

		if ("price_asc".equals(sort)) {
			sorting = Sort.by("price").ascending();
		}

		if ("price_desc".equals(sort)) {
			sorting = Sort.by("price").descending();
		}

		Pageable pageable = PageRequest.of(page, size, sorting);

		return productRepository.findAll(spec, pageable);
	}

	private static Specification<Product> filter(List<Long> brandIds, List<Long> usageIds, String price) {
		Specification<Product> spec = (root, query, cb) -> cb.conjunction();

		// Filter brand
		if (brandIds != null && !brandIds.isEmpty()) {
			spec = spec.and((root, query, cb) -> root.get("brand").get("id").in(brandIds));
		}

		// Filter usage
		if (usageIds != null && !usageIds.isEmpty()) {
			spec = spec.and((root, query, cb) -> {
				Join<Product, ProductUseages> productUsage = root.join("productUsages");
				query.distinct(true);
				return productUsage.get("usage").get("id").in(usageIds);
			});
		}

		// Filter price
		if (price != null && !price.isBlank()) {
			switch (price) {
			case "under10":
				spec = spec.and((root, query, cb) -> cb.lessThan(root.get("price"), BigDecimal.valueOf(10_000_000)));
				break;
			case "10-15":
				spec = spec.and((root, query, cb) -> cb.between(root.get("price"), BigDecimal.valueOf(10_000_000),
						BigDecimal.valueOf(15_000_000)));
				break;
			case "15-20":
				spec = spec.and((root, query, cb) -> cb.between(root.get("price"), BigDecimal.valueOf(15_000_000),
						BigDecimal.valueOf(20_000_000)));
				break;
			case "over20":
				spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("price"), BigDecimal.valueOf(20_000_000)));
				break;
			}
		}

		return spec;
	}
}
