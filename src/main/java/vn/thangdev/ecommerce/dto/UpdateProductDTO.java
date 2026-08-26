package vn.thangdev.ecommerce.dto;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.thangdev.ecommerce.constant.CommonStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
	private Long id;

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

	@NotNull(message = "Trạng thái không được để trống")
	private CommonStatus status;

	private String thumbnail;
}
