/**
 *
 */
package mz.co.grocery.integ.resources.expense;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.expense.in.RegistExpenseUseCase;
import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseReport;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.expense.dto.ExpenseDTO;
import mz.co.grocery.integ.resources.expense.dto.ExpensesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("expenses")
@WebAdapter
public class ExpenseResource extends AbstractResource {

	@Inject
	private RegistExpenseUseCase registExpenseUseCase;

	@Inject
	private ExpensePort expensePort;

	@Autowired
	private DTOMapper<ExpenseDTO, Expense> expenseMapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registExpenses(final ExpensesDTO expensesDTO) throws BusinessException {

		final List<Expense> expenses = expensesDTO.getExpenseDTOs().stream().map(expenseDTO -> this.expenseMapper.toDomain(expenseDTO))
				.collect(Collectors.toList());

		final List<Expense> createdExpenses = this.registExpenseUseCase.registExpenses(this.getContext(), expenses);

		final List<ExpenseDTO> expenseDTOs = createdExpenses.stream().map(expense -> this.expenseMapper.toDTO(
				expense))
				.collect(Collectors.toList());

		return Response.ok(expensesDTO.setExpenseDTOs(expenseDTOs)).build();
	}

	@Path("by-unit-and-period")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findExpensesReportByUnitAndPeriod(@QueryParam("unitUuid") final String unitUuid, @QueryParam("startDate") final String startDate,
			@QueryParam("endDate") final String endDate)
					throws BusinessException {

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();
		final LocalDate sDate = dateAdapter.unmarshal(startDate);
		final LocalDate eDate = dateAdapter.unmarshal(endDate);

		final List<ExpenseReport> expenses = this.expensePort.findExpensesByUnitAndPeriod(unitUuid, sDate, eDate);

		final ExpensesDTO expensesDTO = new ExpensesDTO();
		expensesDTO.setExpensesReport(expenses);

		return Response.ok(expensesDTO).build();
	}

}
