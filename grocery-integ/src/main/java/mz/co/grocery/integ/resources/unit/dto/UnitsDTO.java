/**
 *
 */
package mz.co.grocery.integ.resources.unit.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class UnitsDTO {

	private final List<Unit> units;

	private final Long totalItems;

	private DTOMapper<UnitDTO, Unit> unitMapper;

	public UnitsDTO(final List<Unit> groceries, final Long totalItems, final DTOMapper<UnitDTO, Unit> unitMapper) {
		this.units = groceries;
		this.totalItems = totalItems;
		this.unitMapper = unitMapper;
	}

	public List<UnitDTO> getGroceriesDTO() {
		return Collections.unmodifiableList(this.units.stream().map(unit -> this.unitMapper.toDTO(unit)).collect(Collectors.toList()));
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
