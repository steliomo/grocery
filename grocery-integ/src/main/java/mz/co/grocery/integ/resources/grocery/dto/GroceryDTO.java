/**
 *
 */
package mz.co.grocery.integ.resources.grocery.dto;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.model.UnitType;
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

	private UnitType unitType;

	public GroceryDTO() {
	}

	public GroceryDTO(final Grocery grocery) {
		super(grocery);
		this.mapper(grocery);
	}

	public GroceryDTO(final String groceryUuid) {
		this.setUuid(groceryUuid);
	}

	@Override
	public void mapper(final Grocery grocery) {
		this.name = grocery.getName();
		this.address = grocery.getAddress();
		this.phoneNumber = grocery.getPhoneNumber();
		this.phoneNumberOptional = grocery.getPhoneNumberOptional();
		this.email = grocery.getEmail();
		this.unitType = grocery.getUnitType();
	}

	@Override
	public Grocery get() {
		final Grocery grocery = this.get(new Grocery());
		grocery.setName(this.name);
		grocery.setAddress(this.address);
		grocery.setPhoneNumber(this.phoneNumber);
		grocery.setPhoneNumberOptional(this.phoneNumberOptional);
		grocery.setEmail(this.email);
		grocery.setUnitType(this.unitType);

		return grocery;
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

	public UnitType getUnitType() {
		return this.unitType;
	}
}
