/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionsDTO {

	private final List<ProductDescription> productDescriptions;

	private final Long totalItems;

	private DTOMapper<ProductDescriptionDTO, ProductDescription> productDescriptionMapper;

	public ProductDescriptionsDTO(final List<ProductDescription> productDescriptions, final Long totalItems,
			final DTOMapper<ProductDescriptionDTO, ProductDescription> productDescriptionMapper) {
		this.productDescriptions = productDescriptions;
		this.totalItems = totalItems;
		this.productDescriptionMapper = productDescriptionMapper;
	}

	public List<ProductDescriptionDTO> getProductDescriptionsDTO() {
		return this.productDescriptions.stream().map(productDescription -> this.productDescriptionMapper.toDTO(null)).collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
