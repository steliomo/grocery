package mz.co.grocery.persistence.expense;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.expense.in.ExpenseTypePort;
import mz.co.grocery.core.application.expense.in.RegistExpenseUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ExpenseTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RegistExpensesUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private RegistExpenseUseCase expenseUseCase;

	@Inject
	private ExpenseTypePort expenseTypePort;

	@Inject
	private UnitPort unitPort;

	private List<Expense> expenses;

	@Before
	public void setup() throws BusinessException {
		this.expenses = EntityFactory.gimme(Expense.class, 3, ExpenseTemplate.VALID);

		for (final Expense expense : this.expenses) {
			final ExpenseType expenseType = this.expenseTypePort.createExpenseType(this.getUserContext(), expense.getExpenseType().get());
			final Unit unit = this.unitPort.createUnit(this.getUserContext(), expense.getUnit().get());

			expense.setExpenseType(expenseType);
			expense.setUnit(unit);
		}
	}

	@Test
	public void shouldRegistExpenses() throws BusinessException {
		this.expenseUseCase.registExpenses(this.getUserContext(), this.expenses);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistExpenses() throws BusinessException {
		this.expenseUseCase.registExpenses(this.getUserContext(), new ArrayList<Expense>());
	}
}
