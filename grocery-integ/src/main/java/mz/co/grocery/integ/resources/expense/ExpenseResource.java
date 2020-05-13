/**
 *
 */
package mz.co.grocery.integ.resources.expense;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.core.expense.service.ExpenseService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.expense.dto.ExpenseDTO;
import mz.co.grocery.integ.resources.expense.dto.ExpensesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("expenses")
@Service(ExpenseResource.NAME)
public class ExpenseResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.expense.ExpenseResource";

	@Inject
	private ExpenseService expenseService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registExpenses(final ExpensesDTO expensesDTO) throws BusinessException {

		final List<Expense> expenses = expensesDTO.getExpenseDTOs().stream().map(expenseDTO -> expenseDTO.get())
				.collect(Collectors.toList());

		final List<Expense> createdExpenses = this.expenseService.registExpenses(this.getContext(), expenses);

		final List<ExpenseDTO> expenseDTOs = createdExpenses.stream().map(expense -> new ExpenseDTO(expense))
				.collect(Collectors.toList());

		return Response.ok(expensesDTO.setExpenseDTOs(expenseDTOs)).build();
	}

}
