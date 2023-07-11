/**
 *
 */
package mz.co.grocery.integ.resources.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomersDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("customers")
@WebAdapter
public class CustomerResource extends AbstractResource {

	@Inject
	private CustomerPort customerPort;

	@Autowired
	private DTOMapper<CustomerDTO, Customer> customerMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registCustomer(final CustomerDTO customerDTO) throws BusinessException {

		Customer customer = this.customerMapper.toDomain(customerDTO);

		customer = this.customerPort.createCustomer(this.getContext(), customer);

		return Response.ok(this.customerMapper.toDTO(customer)).build();
	}

	@Path("by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersByUnit(@PathParam("unitUuid") final String unitUuid, @QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersByUnit(unitUuid, currentPage, maxResult);

		final Long totalCustomers = this.customerPort.countCustomersByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, totalCustomers, this.customerMapper)).build();
	}

	@Path("pending-payments-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPendingPaymentsByUnit(@PathParam("unitUuid") final String unitUuid,
			@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithRentPendingPeymentsByUnit(unitUuid, currentPage, maxResult);

		final Long totalCustomers = this.customerPort.countCustomersWithPendingPeymentsByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, totalCustomers, this.customerMapper)).build();
	}

	@Path("find-customers-with-pending-or-incomplete-rent-items-to-return-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(@PathParam("unitUuid") final String unitUuid,
			@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, currentPage,
				maxResult);

		final Long totalCustomers = this.customerPort.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, totalCustomers, this.customerMapper)).build();
	}

	@Path("pending-contract-payment-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithContractPendingPaymentByUnit(@PathParam("unitUuid") final String unitUuid,
			@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult, @QueryParam("currentDate") final String currentDate) throws BusinessException {

		final LocalDateAdapter adapter = new LocalDateAdapter();
		final LocalDate localDate = adapter.unmarshal(currentDate);

		final List<Customer> customers = this.customerPort.findCustomersWithContractPendingPaymentByUnit(unitUuid, currentPage, maxResult,
				localDate);

		final Long totalCustomers = this.customerPort.countCustomersWithContractPendingPaymentByUnit(unitUuid, localDate);

		return Response.ok(new CustomersDTO(customers, totalCustomers, this.customerMapper)).build();
	}

	@Path("find-customers-sale-with-pendind-or-incomplete-payment-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersSaleWithPendindOrIncompletePaymentByUnit(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersSaleWithPendindOrIncompletePaymentByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, BigDecimal.ZERO.longValue(), this.customerMapper)).build();
	}

	@Path("find-customers-with-pending-or-incomplete-rentitems-to-load-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, BigDecimal.ZERO.longValue(), this.customerMapper)).build();
	}

	@Path("find-customers-with-issued-guides-by-type-and-unit")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithIssuedGuidesByTypeAndUnit(@QueryParam("guideType") final GuideType guideType,
			@QueryParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithIssuedGuidesByTypeAndUnit(guideType, unitUuid);

		return Response.ok(new CustomersDTO(customers, BigDecimal.ZERO.longValue(), this.customerMapper)).build();
	}

	@Path("find-payments-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPaymentsByUnit(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithPaymentsByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, BigDecimal.ZERO.longValue(), this.customerMapper)).build();
	}

	@Path("find-customers-with-pending-or-incomplete-delivery-status-sales-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithPendingOrIncompleteDeliveryStatusSales(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, BigDecimal.ZERO.longValue(), this.customerMapper)).build();
	}

	@Path("find-customers-with-delivered-guides-by-unit/{unitUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomersWithDeliveredGuidesByUnit(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Customer> customers = this.customerPort.findCustomersWithDeliveredGuidesByUnit(unitUuid);

		return Response.ok(new CustomersDTO(customers, BigDecimal.ZERO.longValue(), this.customerMapper)).build();
	}
}
