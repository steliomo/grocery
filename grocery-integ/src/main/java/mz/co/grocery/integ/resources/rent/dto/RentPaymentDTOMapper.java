/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class RentPaymentDTOMapper extends AbstractDTOMapper<RentPaymentDTO, RentPayment> implements DTOMapper<RentPaymentDTO, RentPayment> {

	private DTOMapper<RentDTO, Rent> rentMapper;

	@Override
	public RentPaymentDTO toDTO(final RentPayment domain) {
		final RentPaymentDTO dto = new RentPaymentDTO();

		domain.getRent().ifPresent(rent -> {
			rent.cleanLists();
			dto.setRentDTO(this.rentMapper.toDTO(rent));
		});

		dto.setPaymentValue(domain.getPaymentValue());
		dto.setPaymentDate(domain.getPaymentDate());

		return this.toDTO(dto, domain);
	}

	@Override
	public RentPayment toDomain(final RentPaymentDTO dto) {
		final RentPayment domain = new RentPayment();

		dto.getRentDTO().ifPresent(rent -> domain.setRent(this.rentMapper.toDomain(rent)));
		domain.setPaymentValue(dto.getPaymentValue());
		domain.setPaymentDate(dto.getPaymentDate());

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setRentMapper(final DTOMapper<RentDTO, Rent> rentMapper) {
		this.rentMapper = rentMapper;
	}
}
