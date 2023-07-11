/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.rent.Rent;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class RentsDTO {

	private List<Rent> rents;

	private DTOMapper<RentDTO, Rent> rentMapper;

	public RentsDTO(final List<Rent> rents, final DTOMapper<RentDTO, Rent> rentMapper) {
		this.rents = rents;
		this.rentMapper = rentMapper;
	}

	public List<RentDTO> getRentsDTO() {
		return this.rents.stream().map(rent -> this.rentMapper.toDTO(rent)).collect(Collectors.toList());
	}
}
