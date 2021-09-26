/**
 *
 */
package mz.co.grocery.integ.resources.customer.dto;

import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class CustomersDTO {

	private final List<CustomerDTO> customerDTOs;

	private final Long totalCustomers;

	public CustomersDTO(final List<CustomerDTO> customerDTOs, final Long totalCustomers) {
		this.customerDTOs = customerDTOs;
		this.totalCustomers = totalCustomers;
	}

	public List<CustomerDTO> getCustomerDTOs() {
		return this.customerDTOs;
	}

	public Long getTotalCustomers() {
		return this.totalCustomers;
	}
}
