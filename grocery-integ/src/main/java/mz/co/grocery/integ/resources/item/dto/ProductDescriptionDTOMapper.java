/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ProductDescriptionDTOMapper extends AbstractDTOMapper<ProductDescriptionDTO, ProductDescription>
implements DTOMapper<ProductDescriptionDTO, ProductDescription> {

	private DTOMapper<ProductDTO, Product> productMapper;
	private DTOMapper<ProductUnitDTO, ProductUnit> productUnitMapper;

	public ProductDescriptionDTOMapper(final DTOMapper<ProductDTO, Product> productMapper,
			final DTOMapper<ProductUnitDTO, ProductUnit> productUnitMapper) {
		this.productMapper = productMapper;
		this.productUnitMapper = productUnitMapper;
	}

	@Override
	public ProductDescriptionDTO toDTO(final ProductDescription domain) {
		final ProductDescriptionDTO dto = new ProductDescriptionDTO();

		domain.getProduct().ifPresent(product -> dto.setProductDTO(this.productMapper.toDTO(product)));
		domain.getProductUnit().ifPresent(productUnit -> dto.setProductUnitDTO(this.productUnitMapper.toDTO(productUnit)));
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());

		return this.toDTO(dto, domain);
	}

	@Override
	public ProductDescription toDomain(final ProductDescriptionDTO dto) {
		final ProductDescription domain = new ProductDescription();

		dto.getProductDTO().ifPresent(product -> domain.setProduct(this.productMapper.toDomain(product)));
		dto.getProductUnitDTO().ifPresent(productUnit -> domain.setProductUnit(this.productUnitMapper.toDomain(productUnit)));
		domain.setDescription(dto.getDescription());

		return this.toDomain(dto, domain);
	}
}
