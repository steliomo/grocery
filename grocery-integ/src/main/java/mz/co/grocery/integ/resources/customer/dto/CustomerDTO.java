/**
 *
 */
package mz.co.grocery.integ.resources.customer.dto;

import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;

/**
 * @author Stélio Moiane
 *
 */
public class CustomerDTO extends GenericDTO {

	private UnitDTO unitDTO;

	private String name;

	private String address;

	private String contact;

	private String email;

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(final String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
}
