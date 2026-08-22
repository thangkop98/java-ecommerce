package vn.thangdev.ecommerce.dto;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vn.thangdev.ecommerce.constant.CommonStatus;

public class CreateProductDTO {
	@NotNull(message = "Vui lòng chọn thương hiệu")
	private Long brandId;

	@NotNull(message = "Vui lòng chọn danh mục")
	private Long categoryId;

	@NotBlank(message = "Tên sản phẩm không được để trống")
	@Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
	private String name;

	@NotNull(message = "Giá sản phẩm không được để trống")
	@DecimalMin(value = "0.01", message = "Giá sản phẩm phải lớn hơn 0")
	private BigDecimal price;

	@NotNull(message = "Số lượng tồn kho không được để trống")
	@Min(value = 0, message = "Số lượng tồn kho không được âm")
	private Integer stockQuantity;

	private String description;

	private Set<Long> usageIds;

	private CommonStatus status = CommonStatus.active;

	public CreateProductDTO() {
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Long> getUsageIds() {
		return usageIds;
	}

	public void setUsageIds(Set<Long> usageIds) {
		this.usageIds = usageIds;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

}
