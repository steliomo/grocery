/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.grocery.core.product.model.ProductUnitType;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitDTO extends GenericDTO<ProductUnit> {

	private ProductUnitType productUnitType;

	private BigDecimal unit;

	public ProductUnitDTO(final ProductUnit productUnit) {
		super(productUnit);
		this.mapper(productUnit);
	}

	@Override
	public void mapper(final ProductUnit productUnit) {
		this.productUnitType = productUnit.getProductUnitType();
		this.unit = productUnit.getUnit();
	}

	public ProductUnitType getProductUnitType() {
		return this.productUnitType;
	}

	public BigDecimal getUnit() {
		return this.unit;
	}

}
