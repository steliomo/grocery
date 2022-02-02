/**
 *
 */
package mz.co.grocery.integ.resources.customer.dto;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class CustomerDTO extends GenericDTO<Customer> {

	private GroceryDTO unitDTO;

	private String name;

	private String address;

	private String contact;

	private String email;

	public CustomerDTO() {
	}

	public CustomerDTO(final Customer customer) {
		super(customer);
	}

	@Override
	public void mapper(final Customer customer) {

		final Grocery unit = customer.getUnit();

		if (ProxyUtil.isInitialized(unit)) {
			this.unitDTO = new GroceryDTO(unit);
		}

		this.name = customer.getName();
		this.address = customer.getAddress();
		this.contact = customer.getContact();
		this.email = customer.getEmail();
	}

	@Override
	public Customer get() {

		final Customer customer = this.get(new Customer());

		if (this.unitDTO != null) {
			customer.setUnit(this.unitDTO.get());
		}

		customer.setName(this.name);
		customer.setAddress(this.address);
		customer.setContact(this.contact);
		customer.setEmail(this.email);

		return customer;
	}

	public GroceryDTO getUnitDTO() {
		return this.unitDTO;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getContact() {
		return this.contact;
	}

	public String getEmail() {
		return this.email;
	}
}
