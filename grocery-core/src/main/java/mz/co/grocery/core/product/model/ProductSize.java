/**
 *
 */
package mz.co.grocery.core.product.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@Entity
@Table(name = "PRODUCT_SIZES")
public class ProductSize extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "PRODUCT_SIZE_TYPE", nullable = false, length = 20)
	private ProductSizeType productSizeType;

	@NotNull
	@Column(name = "SIZE", nullable = false)
	private BigDecimal size;

	public ProductSizeType getProductSizeType() {
		return this.productSizeType;
	}

	public void setProductSizeType(final ProductSizeType productSizeType) {
		this.productSizeType = productSizeType;
	}

	public BigDecimal getSize() {
		return this.size;
	}

	public void setSize(final BigDecimal size) {
		this.size = size;
	}
}
