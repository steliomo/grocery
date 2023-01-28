/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.util.ApplicationTranslator;

/**
 * @author Stélio Moiane
 *
 */
public class RentsDTO {

	private List<Rent> rents;
	private ApplicationTranslator translator;

	public RentsDTO() {
	}

	public RentsDTO(final List<Rent> rents, final ApplicationTranslator translator) {
		this.rents = rents;
		this.translator = translator;
	}

	public List<RentDTO> getRentsDTO() {
		return this.rents.stream().map(rent -> new RentDTO(rent, this.translator)).collect(Collectors.toList());
	}
}
