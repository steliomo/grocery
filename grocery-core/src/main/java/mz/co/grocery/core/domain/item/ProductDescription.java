/**
 *
 */
package mz.co.grocery.core.domain.item;

import java.util.Optional;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescription extends Domain {

	private Product product;

	private String description;

	private ProductUnit productUnit;

	public Optional<Product> getProduct() {
		return Optional.ofNullable(this.product);
	}

	public void setProduct(final Product product) {
		this.product = product;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Optional<ProductUnit> getProductUnit() {
		return Optional.ofNullable(this.productUnit);
	}

	public void setProductUnit(final ProductUnit productUnit) {
		this.productUnit = productUnit;
	}

	public String getName() {

		final StringBuilder builder = new StringBuilder();

		if (ProductUnitType.NA.equals(this.productUnit.getProductUnitType())) {
			return builder.append(this.product.getName())
					.append(" ")
					.append(this.description).toString();
		}

		return builder
				.append(this.product.getName())
				.append(" ")
				.append(this.description).append(" ")
				.append(this.productUnit.getUnit()).append(" ")
				.append(this.productUnit.getProductUnitType())
				.toString();
	}
}
