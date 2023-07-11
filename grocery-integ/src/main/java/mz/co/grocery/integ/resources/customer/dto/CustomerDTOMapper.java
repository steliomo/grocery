/**
 *
 */
package mz.co.grocery.integ.resources.customer.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class CustomerDTOMapper extends AbstractDTOMapper<CustomerDTO, Customer> implements DTOMapper<CustomerDTO, Customer> {

	private DTOMapper<UnitDTO, Unit> unitMapper;

	public CustomerDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper) {
		this.unitMapper = unitMapper;
	}

	@Override
	public CustomerDTO toDTO(final Customer domain) {
		final CustomerDTO dto = new CustomerDTO();

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		dto.setName(domain.getName());
		dto.setAddress(domain.getAddress());
		dto.setContact(domain.getContact());
		dto.setEmail(domain.getEmail());

		return this.toDTO(dto, domain);
	}

	@Override
	public Customer toDomain(final CustomerDTO dto) {
		final Customer domain = new Customer();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		domain.setName(dto.getName());
		domain.setAddress(dto.getAddress());
		domain.setContact(dto.getContact());
		domain.setEmail(dto.getEmail());

		return this.toDomain(dto, domain);
	}
}
