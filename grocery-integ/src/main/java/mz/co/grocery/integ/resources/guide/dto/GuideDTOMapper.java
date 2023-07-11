/**
 *
 */
package mz.co.grocery.integ.resources.guide.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.rent.dto.RentDTO;
import mz.co.grocery.integ.resources.sale.dto.SaleDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class GuideDTOMapper extends AbstractDTOMapper<GuideDTO, Guide> implements DTOMapper<GuideDTO, Guide> {

	private DTOMapper<GuideItemDTO, GuideItem> guideItemMapper;

	private DTOMapper<RentDTO, Rent> rentMapper;

	private DTOMapper<SaleDTO, Sale> saleMapper;

	public GuideDTOMapper(final DTOMapper<GuideItemDTO, GuideItem> guideItemMapper) {
		this.guideItemMapper = guideItemMapper;
	}

	@Override
	public GuideDTO toDTO(final Guide domain) {
		final GuideDTO dto = new GuideDTO();
		dto.setType(domain.getType());

		domain.getRent().ifPresent(rent -> dto.setRentDTO(this.rentMapper.toDTO(rent)));
		domain.getSale().ifPresent(sale -> dto.setSaleDTO(this.saleMapper.toDTO(sale)));
		dto.setIssueDate(domain.getIssueDate());
		dto.setFilePath(domain.getFilePath());

		domain.getGuideItems().ifPresent(guideItems -> guideItems.forEach(guideItem -> dto.addGuideItemDTO(this.guideItemMapper.toDTO(guideItem))));

		return this.toDTO(dto, domain);
	}

	@Override
	public Guide toDomain(final GuideDTO dto) {
		final Guide domain = new Guide();

		domain.setType(dto.getType());

		dto.getRentDTO().ifPresent(rent -> domain.setRent(this.rentMapper.toDomain(rent)));
		dto.getSaleDTO().ifPresent(sale -> domain.setSale(this.saleMapper.toDomain(sale)));
		domain.setIssueDate(dto.getIssueDate());
		domain.setFilePath(dto.getFilePath());

		dto.getGuideItemsDTO()
		.ifPresent(guideItems -> guideItems.forEach(guideItem -> domain.addGuideItem(this.guideItemMapper.toDomain(guideItem))));

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setRentMapper(final DTOMapper<RentDTO, Rent> rentMapper) {
		this.rentMapper = rentMapper;
	}

	@Inject
	public void setSaleMapper(final DTOMapper<SaleDTO, Sale> saleMapper) {
		this.saleMapper = saleMapper;
	}
}