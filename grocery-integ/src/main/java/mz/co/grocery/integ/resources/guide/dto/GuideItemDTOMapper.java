/**
 *
 */
package mz.co.grocery.integ.resources.guide.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.rent.dto.RentItemDTO;
import mz.co.grocery.integ.resources.sale.dto.SaleItemDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class GuideItemDTOMapper extends AbstractDTOMapper<GuideItemDTO, GuideItem> implements DTOMapper<GuideItemDTO, GuideItem> {

	private DTOMapper<RentItemDTO, RentItem> rentItemMapper;

	private DTOMapper<SaleItemDTO, SaleItem> saleItemMapper;

	public GuideItemDTOMapper(final DTOMapper<RentItemDTO, RentItem> rentItemMapper, final DTOMapper<SaleItemDTO, SaleItem> saleItemMapper) {
		this.rentItemMapper = rentItemMapper;
		this.saleItemMapper = saleItemMapper;
	}

	@Override
	public GuideItemDTO toDTO(final GuideItem domain) {
		final GuideItemDTO dto = new GuideItemDTO();

		domain.getRentItem().ifPresent(rentItem -> dto.setRentItemDTO(this.rentItemMapper.toDTO(rentItem)));
		domain.getSaleItem().ifPresent(saleItem -> dto.setSaleItemDTO(this.saleItemMapper.toDTO(saleItem)));
		dto.setQuantity(domain.getQuantity());

		return this.toDTO(dto, domain);
	}

	@Override
	public GuideItem toDomain(final GuideItemDTO dto) {
		final GuideItem domain = new GuideItem();

		dto.getRentItemDTO().ifPresent(rentItem -> domain.setRentItem(this.rentItemMapper.toDomain(rentItem)));
		dto.getSaleItemDTO().ifPresent(saleItem -> domain.setSaleItem(this.saleItemMapper.toDomain(saleItem)));
		domain.setQuantity(dto.getQuantity());

		return this.toDomain(dto, domain);
	}

}
