package vn.thangdev.ecommerce.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_usages", uniqueConstraints = {
		@UniqueConstraint(name = "uk_product_usage", columnNames = { "product_id", "usage_id" }) })
@Getter
@Setter
public class ProductUseages {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "usage_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Useage usage;
}
