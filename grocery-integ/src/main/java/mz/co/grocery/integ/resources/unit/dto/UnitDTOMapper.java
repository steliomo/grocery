/**
 *
 */
package mz.co.grocery.integ.resources.unit.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class UnitDTOMapper extends AbstractDTOMapper<UnitDTO, Unit> implements DTOMapper<UnitDTO, Unit> {

	@Override
	public UnitDTO toDTO(final Unit domain) {
		final UnitDTO dto = new UnitDTO();

		dto.setName(domain.getName());
		dto.setAddress(domain.getAddress());
		dto.setPhoneNumber(domain.getPhoneNumber());
		dto.setPhoneNumberOptional(domain.getPhoneNumberOptional());
		dto.setEmail(domain.getEmail());
		dto.setUnitType(domain.getUnitType());
		dto.setBalance(domain.getBalance());

		return this.toDTO(dto, domain);
	}

	@Override
	public Unit toDomain(final UnitDTO dto) {
		final Unit domain = new Unit();

		domain.setName(dto.getName());
		domain.setAddress(dto.getAddress());
		domain.setPhoneNumber(dto.getPhoneNumber());
		domain.setPhoneNumberOptional(dto.getPhoneNumberOptional());
		domain.setEmail(dto.getEmail());
		domain.setUnitType(dto.getUnitType());

		dto.getBalance().ifPresent(balance -> domain.setBalance(balance));

		return this.toDomain(dto, domain);
	}

}
