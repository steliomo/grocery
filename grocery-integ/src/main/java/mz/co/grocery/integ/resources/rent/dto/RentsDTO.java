/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.rent.model.Rent;

/**
 * @author Stélio Moiane
 *
 */
public class RentsDTO {

	private List<Rent> rents;

	public RentsDTO() {
	}

	public RentsDTO(final List<Rent> rents) {
		this.rents = rents;
	}

	public List<RentDTO> getRentsDTO() {
		return this.rents.stream().map(rent -> new RentDTO(rent)).collect(Collectors.toList());
	}
}
