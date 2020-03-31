/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import java.util.Collections;
import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionsDTO {

	private final List<ProductDescriptionDTO> productDescriptionsDTO;

	private final Long totalItems;

	public ProductDescriptionsDTO(final List<ProductDescriptionDTO> productDescriptionsDTO, final Long totalItems) {
		this.productDescriptionsDTO = productDescriptionsDTO;
		this.totalItems = totalItems;
	}

	public List<ProductDescriptionDTO> getProductDescriptionsDTO() {
		return Collections.unmodifiableList(this.productDescriptionsDTO);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
