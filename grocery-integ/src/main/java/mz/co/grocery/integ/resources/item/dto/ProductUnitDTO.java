/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.domain.item.ProductUnitType;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitDTO extends GenericDTO {

	private ProductUnitType productUnitType;

	private BigDecimal unit;

	public ProductUnitType getProductUnitType() {
		return this.productUnitType;
	}

	public void setProductUnitType(final ProductUnitType productUnitType) {
		this.productUnitType = productUnitType;
	}

	public BigDecimal getUnit() {
		return this.unit;
	}

	public void setUnit(final BigDecimal unit) {
		this.unit = unit;
	}

	public String getName() {
		final StringBuilder builder = new StringBuilder();
		builder.append(this.unit).append(" ").append(this.productUnitType);
		return builder.toString();
	}
}
