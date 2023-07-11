/**
 *
 */
package mz.co.grocery.core.domain.item;

import java.math.BigDecimal;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnit extends Domain {

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
}
