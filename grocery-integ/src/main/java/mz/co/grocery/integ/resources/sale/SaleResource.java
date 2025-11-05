/**
 *
 */
package mz.co.grocery.integ.resources.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.application.pos.in.CancelTableUseCase;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.pos.in.PayDeptUseCase;
import mz.co.grocery.core.application.pos.in.RegistTableItemsUseCase;
import mz.co.grocery.core.application.pos.in.SendCustomerDebtUseCase;
import mz.co.grocery.core.application.pos.in.SendTableBillUseCase;
import mz.co.grocery.core.application.pos.out.SaleListner;
import mz.co.grocery.core.application.pos.out.SaleNotifier;
import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.sale.in.RegistCreditSaleUseCase;
import mz.co.grocery.core.application.sale.in.SalePaymentUseCase;
import mz.co.grocery.core.application.sale.in.SaleUseCase;
import mz.co.grocery.core.application.sale.in.SendDailySalesReportUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.service.CashSaleService;
import mz.co.grocery.core.application.sale.service.InstallmentSaleService;
import mz.co.grocery.core.application.unit.out.UnitUserPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.expense.ExpenseReport;
import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.sale.dto.DebtDTO;
import mz.co.grocery.integ.resources.sale.dto.DebtDTOMapper;
import mz.co.grocery.integ.resources.sale.dto.DebtItemDTOMapper;
import mz.co.grocery.integ.resources.sale.dto.SaleDTO;
import mz.co.grocery.integ.resources.sale.dto.SalePaymentDTO;
import mz.co.grocery.integ.resources.sale.dto.SalesDTO;
import mz.co.grocery.persistence.pos.adapter.SendSaleTemplateMessageToWhastAppListner;
import mz.co.grocery.persistence.pos.adapter.SendSaleToDBListner;
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

	@Inject
	private OpenTableUseCase openTableUseCase;

	@Inject
	private RegistTableItemsUseCase registTableItemsUseCase;

	@Inject
	private SendTableBillUseCase sendTableBillUseCase;

	@Inject
	private SaleNotifier saleNotifier;

	@Inject
	private SendDailySalesReportUseCase sendDailySalesReportUseCase;

	@Autowired
	@BeanQualifier(SendSaleToDBListner.NAME)
	private SaleListner sendToDBListner;

	@Autowired
	@BeanQualifier(SendSaleTemplateMessageToWhastAppListner.NAME)
	private SaleListner sendTemplateMessageListner;

	@Inject
	private CancelTableUseCase cancelTableUseCase;

	@Inject
	private RegistCreditSaleUseCase registCreditSaleUseCase;

	@Inject
	private PayDeptUseCase payDebtUseCase;

	@Inject
	private DebtItemDTOMapper deptItemDTOMapper;

	@Inject
	private SaleItemPort saleItemPort;

	@Inject
	private SendCustomerDebtUseCase sendCustomerDebtUseCase;

	@Inject
	private DebtDTOMapper debtDTOMapper;

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

		default:
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

	@Path("open-table")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response openTable(final SaleDTO tableDTO) throws BusinessException {

		this.saleNotifier.registListner(this.sendToDBListner);
		this.saleNotifier.registListner(this.sendTemplateMessageListner);
		this.openTableUseCase.setSaleNotifier(this.saleNotifier);

		final Sale table = this.openTableUseCase.openTable(this.getContext(), this.saleMapper.toDomain(tableDTO));

		return Response.ok(this.saleMapper.toDTO(table)).build();
	}

	@Path("regist-items")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registItems(final SaleDTO tableDTO) throws BusinessException {

		final Sale table = this.registTableItemsUseCase.registTableItems(this.getContext(), this.saleMapper.toDomain(tableDTO));

		return Response.ok(this.saleMapper.toDTO(table)).build();
	}

	@Path("fetch-opened-tables")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchOpenedTables(@QueryParam("unitUuid") final String unitUuid)
			throws BusinessException {

		final List<Sale> tables = this.salePort.fetchOpenedTables(unitUuid);

		final SalesDTO tablesDTO = new SalesDTO();

		tables.forEach(table -> tablesDTO.addSale(this.saleMapper.toDTO(table)));

		return Response.ok(tablesDTO).build();
	}

	@Path("fetch-table-by-uuid/{tableUuid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchTableByUuid(@PathParam("tableUuid") final String tableUuid) throws BusinessException {

		final Sale table = this.salePort.fetchByUuid(tableUuid);

		return Response.ok(this.saleMapper.toDTO(table)).build();
	}

	@POST
	@Path("send-table-bill")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendTableBill(final SaleDTO saleDTO) throws BusinessException {
		Sale sale = this.saleMapper.toDomain(saleDTO);

		sale = this.sendTableBillUseCase.sendBill(sale);

		return Response.ok(this.saleMapper.toDTO(sale)).build();
	}

	@Path("cancel-table")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelTable(final SaleDTO tableDTO) throws BusinessException {

		final Sale table = this.cancelTableUseCase.cancel(this.getContext(), this.saleMapper.toDomain(tableDTO));

		return Response.ok(this.saleMapper.toDTO(table)).build();
	}

	@Scheduled(cron = "0 15 0 * * ?")
	public void sendSalesDailyReport() throws BusinessException {
		final LocalDate reportDate = LocalDate.now().minusDays(1);

		this.sendDailySalesReportUseCase.sendReport(reportDate);
	}

	@Path("regist-credit-sale/{saleUuid}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registCreditSale(@PathParam("saleUuid") final String saleUuid) throws BusinessException {

		final Sale sale = this.registCreditSaleUseCase.regist(this.getContext(), saleUuid);

		return Response.ok(this.saleMapper.toDTO(sale)).build();
	}

	@Path("pay-debt")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response payDebt(final DebtDTO debt) throws BusinessException {

		final Debt paidDebt = this.payDebtUseCase.pay(this.getContext(), this.debtDTOMapper.toDomain(debt));

		return Response.ok(this.debtDTOMapper.toDTO(paidDebt)).build();
	}

	@Path("find-debt-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findDebtByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final Debt debt = this.salePort.findDeptByCustomer(customerUuid);

		return Response.ok(this.debtDTOMapper.toDTO(debt)).build();
	}

	@Path("find-debt-items-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findDebtItemsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<DebtItem> debtItems = this.saleItemPort.findDeptItemsByCustomer(customerUuid);

		return Response.ok(debtItems.stream().map(this.deptItemDTOMapper::toDTO).collect(Collectors.toList())).build();
	}

	@Path("send-customer-debt")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendCustomerDebt(final DebtDTO debt) throws BusinessException {

		this.sendCustomerDebtUseCase.sendDebt(this.debtDTOMapper.toDomain(debt));

		return Response.ok().build();
	}
}
