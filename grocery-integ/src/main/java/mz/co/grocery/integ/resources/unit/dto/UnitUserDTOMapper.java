/**
 *
 */
package mz.co.grocery.integ.resources.unit.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class UnitUserDTOMapper extends AbstractDTOMapper<UnitUserDTO, UnitUser> implements DTOMapper<UnitUserDTO, UnitUser> {

	private DTOMapper<UnitDTO, Unit> unitMapper;

	public UnitUserDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper) {
		this.unitMapper = unitMapper;
	}

	@Override
	public UnitUserDTO toDTO(final UnitUser domain) {
		final UnitUserDTO dto = new UnitUserDTO();

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		dto.setUser(domain.getUser());
		dto.setUserRole(domain.getUserRole());
		dto.setExpiryDate(domain.getExpiryDate());

		return this.toDTO(dto, domain);
	}

	@Override
	public UnitUser toDomain(final UnitUserDTO dto) {
		final UnitUser domain = new UnitUser();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		domain.setUser(dto.getUser());
		domain.setUserRole(dto.getUserRole());
		domain.setExpiryDate(dto.getExpiryDate());

		return this.toDomain(dto, domain);
	}

}
