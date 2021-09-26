/**
 *
 */
package mz.co.grocery.integ.resources.customer;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.service.CustomerQueryService;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomersDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("customers")
@Service(CustomerResource.NAME)
public class CustomerResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.customer.CustomerResource";

	@Inject
	private CustomerService customerService;

	@Inject
	private CustomerQueryService customerQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registCustomer(final CustomerDTO customerDTO) throws BusinessException {

		this.customerService.createCustomer(this.getContext(), customerDTO.get());

		return Response.ok(customerDTO).build();
	}

	@Path("by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersByUnit(@PathParam("unitUuid") final String unitUuid, @QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<Customer> customers = this.customerQueryService.findCustomersByUnit(unitUuid, currentPage, maxResult);

		final List<CustomerDTO> customerDTOs = customers.stream().map(customer -> new CustomerDTO(customer)).collect(Collectors.toList());

		final Long totalCustomers = this.customerQueryService.countCustomersByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customerDTOs, totalCustomers)).build();
	}

	@Path("pending-payments-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPendingPaymentsByUnit(@PathParam("unitUuid") final String unitUuid,
			@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<Customer> customers = this.customerQueryService.findCustomersWithPendingPeymentsByUnit(unitUuid, currentPage, maxResult);

		final List<CustomerDTO> customerDTOs = customers.stream().map(customer -> new CustomerDTO(customer)).collect(Collectors.toList());

		final Long totalCustomers = this.customerQueryService.countCustomersWithPendingPeymentsByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customerDTOs, totalCustomers)).build();
	}

	@Path("pending-devolutions-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPendingDevolutionsByUnit(@PathParam("unitUuid") final String unitUuid,
			@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<Customer> customers = this.customerQueryService.findCustomersWithPendingDevolutionsByUnit(unitUuid, currentPage, maxResult);

		final List<CustomerDTO> customerDTOs = customers.stream().map(customer -> new CustomerDTO(customer)).collect(Collectors.toList());

		final Long totalCustomers = this.customerQueryService.countCustomersWithPendingDevolutionsByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customerDTOs, totalCustomers)).build();
	}
}
