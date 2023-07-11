/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ProductUnitDTOMapper extends AbstractDTOMapper<ProductUnitDTO, ProductUnit> implements DTOMapper<ProductUnitDTO, ProductUnit> {

	@Override
	public ProductUnitDTO toDTO(final ProductUnit domain) {
		final ProductUnitDTO dto = new ProductUnitDTO();

		dto.setProductUnitType(domain.getProductUnitType());
		dto.setUnit(domain.getUnit());

		return this.toDTO(dto, domain);
	}

	@Override
	public ProductUnit toDomain(final ProductUnitDTO dto) {
		final ProductUnit domain = new ProductUnit();

		domain.setProductUnitType(dto.getProductUnitType());
		domain.setUnit(dto.getUnit());

		return this.toDomain(dto, domain);
	}
}
