/**
 *
 */
package mz.co.grocery.core.expense;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.expense.in.RegistExpenseUseCase;
import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.application.expense.service.ExpenseService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.fixturefactory.ExpenseTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ExpensePort expensePort;

	@InjectMocks
	private final RegistExpenseUseCase expenseService = new ExpenseService(this.expensePort);

	@Test
	public void shouldRegistExpenses() throws BusinessException {
		final List<Expense> expenses = EntityFactory.gimme(Expense.class, 5, ExpenseTemplate.VALID);
		final UserContext userContext = this.getUserContext();
		this.expenseService.registExpenses(userContext, expenses);

		for (final Expense expense : expenses) {
			Mockito.verify(this.expensePort, Mockito.times(1)).createExpense(userContext, expense);
		}
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistEmptyExpenses() throws BusinessException {
		final List<Expense> expenses = new ArrayList<>();
		this.expenseService.registExpenses(this.getUserContext(), expenses);
	}
}
