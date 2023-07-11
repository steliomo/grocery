/**
 *
 */
package mz.co.grocery.integ.resources.customer.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class CustomersDTO {

	private final List<Customer> customers;

	private Long totalCustomers;

	private DTOMapper<CustomerDTO, Customer> customerMapper;

	public CustomersDTO(final List<Customer> customers, final Long totalCustomers, final DTOMapper<CustomerDTO, Customer> customerMapper) {
		this.customers = customers;
		this.totalCustomers = totalCustomers;
		this.customerMapper = customerMapper;
	}

	public List<CustomerDTO> getCustomerDTOs() {
		return this.customers.stream().map(customer -> this.customerMapper.toDTO(customer)).collect(Collectors.toList());
	}

	public Long getTotalCustomers() {
		return this.totalCustomers;
	}
}
