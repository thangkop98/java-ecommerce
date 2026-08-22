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

@Entity
@Table(name = "product_usages", uniqueConstraints = {
		@UniqueConstraint(name = "uk_product_usage", columnNames = { "product_id", "usage_id" }) })
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Useage getUsage() {
		return usage;
	}

	public void setUsage(Useage usage) {
		this.usage = usage;
	}

}
