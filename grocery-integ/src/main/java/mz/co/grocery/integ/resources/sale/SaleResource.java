/**
 *
 */
package mz.co.grocery.integ.resources.sale;

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

import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.sale.in.SalePaymentUseCase;
import mz.co.grocery.core.application.sale.in.SaleUseCase;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.service.CashSaleService;
import mz.co.grocery.core.application.sale.service.InstallmentSaleService;
import mz.co.grocery.core.application.unit.out.UnitUserPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.expense.ExpenseReport;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.sale.dto.SaleDTO;
import mz.co.grocery.integ.resources.sale.dto.SalePaymentDTO;
import mz.co.grocery.integ.resources.sale.dto.SalesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("sales")
@WebAdapter
public class SaleResource extends AbstractResource {

	@Autowired
	@BeanQualifier(CashSaleService.NAME)
	private SaleUseCase cashSaleService;

	@Autowired
	@BeanQualifier(InstallmentSaleService.NAME)
	private SaleUseCase installmentSaleService;

	@Inject
	private SalePort salePort;

	@Inject
	private ExpensePort expensePort;

	@Inject
	private UnitUserPort unitUserPort;

	@Inject
	private RentPaymentPort rentPaymentPort;

	@Inject
	private SalePaymentUseCase salePaymentUseCase;

	@Autowired
	private DTOMapper<SaleDTO, Sale> saleMapper;

	@Autowired
	private DTOMapper<SalePaymentDTO, SalePayment> salePaymentMapper;

	@Autowired
	private DTOMapper<CustomerDTO, Customer> customerMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registSale(final SaleDTO saleDTO) throws BusinessException {
		Sale sale = this.saleMapper.toDomain(saleDTO);

		switch (saleDTO.getSaleType()) {
		case CASH:
			sale = this.cashSaleService.registSale(this.getContext(), sale);
			break;

		case INSTALLMENT:
			sale = this.installmentSaleService.registSale(this.getContext(), sale);
			break;
		}

		return Response.ok(this.saleMapper.toDTO(sale)).build();
	}

	@GET
	@Path("per-period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSalesPerPeriod(@QueryParam("groceryUuid") final String unitUuid,
			@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate)
					throws BusinessException {

		final UnitUser user = this.unitUserPort.fetchUnitUserByUser(this.getContext().getUuid());

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();
		final LocalDate sDate = dateAdapter.unmarshal(startDate);
		final LocalDate eDate = dateAdapter.unmarshal(endDate);

		final List<SaleReport> sales = this.salePort.findSalesByUnitAndPeriod(unitUuid, sDate, eDate);

		final List<SaleReport> rents = this.rentPaymentPort.findSalesByUnitAndPeriod(unitUuid, sDate, eDate);

		final BigDecimal expense = this.expensePort.findExpensesValueByUnitAndPeriod(unitUuid, sDate, eDate);

		final SalesDTO buildMonthly = new SalesDTO(user).setSalesBalance(sales).setRentsBalance(rents).setExpense(expense).buildPeriod();

		return Response.ok(buildMonthly).build();
	}

	@GET
	@Path("monthly-per-period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findMonthySalesPerPeriod(@QueryParam("groceryUuid") final String unitUuid,
			@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate)
					throws BusinessException {

		final UnitUser user = this.unitUserPort.fetchUnitUserByUser(this.getContext().getUuid());

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();
		final LocalDate sDate = dateAdapter.unmarshal(startDate);
		final LocalDate eDate = dateAdapter.unmarshal(endDate);

		final List<SaleReport> sales = this.salePort.findSalesByUnitAndMonthlyPeriod(unitUuid, sDate, eDate);

		final List<SaleReport> rents = this.rentPaymentPort.findSalesByUnitAndMonthlyPeriod(unitUuid, sDate, eDate);

		final List<ExpenseReport> expenses = this.expensePort.findMonthyExpensesByGroceryAndPeriod(unitUuid,
				sDate,
				eDate);

		final SalesDTO salesDTO = new SalesDTO(user).setSalesBalance(sales).setRentsBalance(rents).setExpenses(expenses).buildMonthly();

		return Response.ok(salesDTO).build();
	}

	@POST
	@Path("sale-payment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salePayment(final SalePaymentDTO salePaymentDTO) throws BusinessException {

		final SalePayment salePayment = this.salePaymentUseCase.payInstallmentSale(this.getContext(), salePaymentDTO.getSaleUuid(),
				salePaymentDTO.getPaymentValue(),
				salePaymentDTO.getPaymentDate());

		return Response.ok(this.salePaymentMapper.toDTO(salePayment)).build();
	}

	@GET
	@Path("find-pending-or-incomplete-sales-by-customer/{customerUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPendingOrIncompleteSalesByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUuid(customerUuid);

		final List<Sale> sales = this.salePort.findPendingOrImpletePaymentSaleStatusByCustomer(this.customerMapper.toDomain(customerDTO));

		final SalesDTO salesDTO = new SalesDTO();

		sales.forEach(sale -> salesDTO.addSale(this.saleMapper.toDTO(sale)));

		return Response.ok(salesDTO).build();
	}

	@GET
	@Path("fetch-sales-with-pending-or-incomplete-delivery-status-by-customer/{customerUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(@PathParam("customerUuid") final String customerUuid)
			throws BusinessException {

		final CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUuid(customerUuid);

		final List<Sale> sales = this.salePort.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(this.customerMapper.toDomain(customerDTO));

		final SalesDTO salesDTO = new SalesDTO();

		sales.forEach(sale -> salesDTO.addSale(this.saleMapper.toDTO(sale)));

		return Response.ok(salesDTO).build();
	}

	@GET
	@Path("fetch-sales-with-delivery-guides-by-customer/{customerUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchSalesWithDeliveryGuidesByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUuid(customerUuid);

		final List<Sale> sales = this.salePort.fetchSalesWithDeliveryGuidesByCustomer(this.customerMapper.toDomain(customerDTO));

		final SalesDTO salesDTO = new SalesDTO();

		sales.forEach(sale -> salesDTO.addSale(this.saleMapper.toDTO(sale)));

		return Response.ok(salesDTO).build();
	}
}
