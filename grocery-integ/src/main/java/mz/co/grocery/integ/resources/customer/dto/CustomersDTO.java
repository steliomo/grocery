/**
 *
 */
package mz.co.grocery.integ.resources.customer.dto;

import java.util.ArrayList;
import java.util.List;

import mz.co.grocery.core.customer.model.Customer;

/**
 * @author Stélio Moiane
 *
 */
public class CustomersDTO {

	private final List<CustomerDTO> customerDTOs;

	private Long totalCustomers;

	public CustomersDTO(final List<CustomerDTO> customerDTOs, final Long totalCustomers) {
		this.customerDTOs = customerDTOs;
		this.totalCustomers = totalCustomers;
	}

	public CustomersDTO(final List<Customer> customers) {
		this.customerDTOs = new ArrayList<>();
		customers.forEach(customer -> this.customerDTOs.add(new CustomerDTO(customer)));
	}

	public List<CustomerDTO> getCustomerDTOs() {
		return this.customerDTOs;
	}

	public Long getTotalCustomers() {
		return this.totalCustomers;
	}
}
