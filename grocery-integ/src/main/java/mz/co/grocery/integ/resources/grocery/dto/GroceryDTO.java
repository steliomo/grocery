/**
 *
 */
package mz.co.grocery.integ.resources.grocery.dto;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryDTO extends GenericDTO<Grocery> {

	private String name;

	private String address;

	private String phoneNumber;

	private String phoneNumberOptional;

	private String email;

	public GroceryDTO(final Grocery grocery) {
		super(grocery);
		this.mapper(grocery);
	}

	@Override
	public void mapper(final Grocery grocery) {
		this.name = grocery.getName();
		this.address = grocery.getAddress();
		this.phoneNumber = grocery.getPhoneNumber();
		this.phoneNumberOptional = grocery.getPhoneNumberOptional();
		this.email = grocery.getEmail();
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getPhoneNumberOptional() {
		return this.phoneNumberOptional;
	}

	public String getEmail() {
		return this.email;
	}
}
