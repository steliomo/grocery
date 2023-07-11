/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentPayment;
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
public class RentDTOMapper extends AbstractDTOMapper<RentDTO, Rent> implements DTOMapper<RentDTO, Rent> {

	private DTOMapper<UnitDTO, Unit> unitMapper;

	private DTOMapper<CustomerDTO, Customer> customerMapper;

	private DTOMapper<RentItemDTO, RentItem> rentItemMapper;

	private DTOMapper<RentPaymentDTO, RentPayment> rentPaymentMapper;

	private DTOMapper<GuideDTO, Guide> guideMapper;

	public RentDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper, final DTOMapper<CustomerDTO, Customer> customerMapper,
			final DTOMapper<RentItemDTO, RentItem> rentItemMapper) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
		this.rentItemMapper = rentItemMapper;
	}

	@Override
	public RentDTO toDTO(final Rent domain) {
		final RentDTO dto = new RentDTO();
		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		domain.getCustomer().ifPresent(customer -> dto.setCustomerDTO(this.customerMapper.toDTO(customer)));

		dto.setRentDate(domain.getRentDate());
		dto.setTotalEstimated(domain.getTotalEstimated());
		dto.setTotalCalculated(domain.getTotalCalculated());
		dto.setTotalPaid(domain.getTotalPayment());
		dto.setTotalToPay(domain.getTotalToPay());
		dto.setTotalToRefund(domain.totalToRefund());
		dto.setPaymentStatus(new EnumDTO(domain.getPaymentStatus().toString(), domain.getPaymentStatus().toString()));

		domain.getRentItems().ifPresent(rentItems -> rentItems.forEach(rentItem -> dto.addRentItemDTO(this.rentItemMapper.toDTO(rentItem))));
		domain.getRentPayments()
		.ifPresent(rentPayments -> rentPayments.forEach(rentPayment -> dto.addRentPaymentDTO(this.rentPaymentMapper.toDTO(rentPayment))));
		domain.getGuides().ifPresent(guides -> guides.forEach(guide -> dto.addGuideDTO(this.guideMapper.toDTO(guide))));

		return this.toDTO(dto, domain);
	}

	@Override
	public Rent toDomain(final RentDTO dto) {
		final Rent domain = new Rent();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		dto.getCustomerDTO().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setRentPaymentMapper(final DTOMapper<RentPaymentDTO, RentPayment> rentPaymentMapper) {
		this.rentPaymentMapper = rentPaymentMapper;
	}

	@Inject
	public void setGuideMapper(final DTOMapper<GuideDTO, Guide> guideMapper) {
		this.guideMapper = guideMapper;
	}
}
