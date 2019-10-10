/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import java.util.Collections;
import java.util.List;

import mz.co.grocery.core.product.model.ProductDescription;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionsDTO {

	private final List<ProductDescription> productDescriptions;

	private final Long totalItems;

	public ProductDescriptionsDTO(final List<ProductDescription> productDescriptions, final Long totalItems) {
		this.productDescriptions = productDescriptions;
		this.totalItems = totalItems;
	}

	public List<ProductDescription> getProductDescriptions() {
		return Collections.unmodifiableList(this.productDescriptions);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
