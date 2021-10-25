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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.grocery.core.expense.service.ExpenseQueryService;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.service.GroceryUserQueryService;
import mz.co.grocery.core.rent.service.RentPaymentQueryService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.core.sale.service.SaleQueryService;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.sale.dto.SaleDTO;
import mz.co.grocery.integ.resources.sale.dto.SalesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("sales")
@Service(SaleResource.NAME)
public class SaleResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.sale.SaleResource";

	@Inject
	private SaleService saleService;

	@Inject
	private SaleQueryService saleQueryService;

	@Inject
	private ExpenseQueryService expenseQueryService;

	@Inject
	private GroceryUserQueryService groceryUserQueryService;

	@Inject
	private RentPaymentQueryService rentPaymentQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registSale(final SaleDTO saleDTO) throws BusinessException {
		final Sale sale = this.saleService.registSale(this.getContext(), saleDTO.get());
		return Response.ok(new SaleDTO(sale)).build();
	}

	@GET
	@Path("per-period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSalesPerPeriod(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate)
					throws BusinessException {

		final GroceryUser user = this.groceryUserQueryService.fetchGroceryUserByUser(this.getContext().getUuid());

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();
		final LocalDate sDate = dateAdapter.unmarshal(startDate);
		final LocalDate eDate = dateAdapter.unmarshal(endDate);

		final List<SaleReport> sales = this.saleQueryService.findSalesPerPeriod(groceryUuid, sDate, eDate);

		final List<SaleReport> rents = this.rentPaymentQueryService.findSalesByUnitAndPeriod(groceryUuid, sDate, eDate);

		final BigDecimal expense = this.expenseQueryService.findExpensesValueByGroceryAndPeriod(groceryUuid, sDate,
				eDate);

		final SalesDTO buildMonthly = new SalesDTO(user).setSalesBalance(sales).setRentsBalance(rents).setExpense(expense).buildPeriod();

		return Response.ok(buildMonthly).build();
	}

	@GET
	@Path("monthly-per-period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findMonthySalesPerPeriod(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate)
					throws BusinessException {

		final GroceryUser user = this.groceryUserQueryService.fetchGroceryUserByUser(this.getContext().getUuid());

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();
		final LocalDate sDate = dateAdapter.unmarshal(startDate);
		final LocalDate eDate = dateAdapter.unmarshal(endDate);

		final List<SaleReport> sales = this.saleQueryService.findMonthlySalesPerPeriod(groceryUuid, sDate, eDate);

		final List<SaleReport> rents = this.rentPaymentQueryService.findSalesByUnitAndMonthlyPeriod(groceryUuid, sDate, eDate);

		final List<ExpenseReport> expenses = this.expenseQueryService.findMonthyExpensesByGroceryAndPeriod(groceryUuid,
				sDate,
				eDate);

		final SalesDTO salesDTO = new SalesDTO(user).setSalesBalance(sales).setRentsBalance(rents).setExpenses(expenses).buildMonthly();

		return Response.ok(salesDTO).build();
	}
}
