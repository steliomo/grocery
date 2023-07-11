/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.common.EnumDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.guide.dto.GuideDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class SaleDTOMapper extends AbstractDTOMapper<SaleDTO, Sale> implements DTOMapper<SaleDTO, Sale> {

	private DTOMapper<UnitDTO, Unit> unitMapper;

	private DTOMapper<CustomerDTO, Customer> customerMapper;

	private DTOMapper<SaleItemDTO, SaleItem> saleItemMapper;

	private DTOMapper<GuideDTO, Guide> guideMapper;

	public SaleDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper, final DTOMapper<CustomerDTO, Customer> customerMapper,
			final DTOMapper<SaleItemDTO, SaleItem> saleItemMapper) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
		this.saleItemMapper = saleItemMapper;
	}

	@Override
	public SaleDTO toDTO(final Sale domain) {
		final SaleDTO dto = new SaleDTO();

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		domain.getCustomer().ifPresent(customer -> dto.setCustomerDTO(this.customerMapper.toDTO(customer)));

		dto.setSaleDate(domain.getSaleDate());
		dto.setBilling(domain.getBilling());
		dto.setTotal(domain.getTotal());
		dto.setSaleType(domain.getSaleType());
		dto.setTotalPaid(domain.getTotalPaid());
		dto.setDueDate(domain.getDueDate());
		dto.setSaleStatus(new EnumDTO(domain.getSaleStatus().toString(), domain.getSaleStatus().toString()));
		dto.setDeliveryStatus(new EnumDTO(domain.getDeliveryStatus().toString(), domain.getDeliveryStatus().toString()));

		domain.getItems().ifPresent(items -> items.forEach(saleItem -> dto.addSaleItemsDTO(this.saleItemMapper.toDTO(saleItem))));
		domain.getGuides().ifPresent(guides -> guides.forEach(guide -> dto.addGuideDTO(this.guideMapper.toDTO(guide))));

		return this.toDTO(dto, domain);
	}

	@Override
	public Sale toDomain(final SaleDTO dto) {
		final Sale domain = new Sale();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		dto.getCustomerDTO().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		domain.setSaleDate(dto.getSaleDate());
		domain.setBilling(dto.getBilling());
		domain.setTotal(dto.getTotal());
		domain.setSaleType(dto.getSaleType());
		domain.setTotalPaid(dto.getTotalPaid());
		domain.setDueDate(dto.getDueDate());

		dto.getSaleStatus().ifPresent(saleStatus -> domain.setSaleStatus(SaleStatus.valueOf(saleStatus.getValue())));
		dto.getDeliveryStatus().ifPresent(deliveryStatus -> domain.setDeliveryStatus(DeliveryStatus.valueOf(deliveryStatus.getValue())));

		dto.getSaleItemsDTO().ifPresent(items -> items.forEach(saleItem -> domain.addItem(this.saleItemMapper.toDomain(saleItem))));
		dto.getGuidesDTO().ifPresent(guides -> guides.forEach(guide -> domain.addGuide(this.guideMapper.toDomain(guide))));

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setGuideMapper(final DTOMapper<GuideDTO, Guide> guideMapper) {
		this.guideMapper = guideMapper;
	}
}
