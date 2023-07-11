/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ProductDTOMapper extends AbstractDTOMapper<ProductDTO, Product> implements DTOMapper<ProductDTO, Product> {

	@Override
	public ProductDTO toDTO(final Product domain) {
		final ProductDTO dto = new ProductDTO();
		dto.setName(domain.getName());
		return this.toDTO(dto, domain);
	}

	@Override
	public Product toDomain(final ProductDTO dto) {
		final Product domain = new Product();
		domain.setName(dto.getName());
		return this.toDomain(dto, domain);
	}
}
