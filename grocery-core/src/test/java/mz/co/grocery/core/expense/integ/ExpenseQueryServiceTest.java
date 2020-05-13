/**
 *
 */
package mz.co.grocery.core.expense.integ;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.grocery.core.expense.service.ExpenseQueryService;
import mz.co.grocery.core.expense.service.ExpenseService;
import mz.co.grocery.core.expense.service.ExpenseTypeService;
import mz.co.grocery.core.fixturefactory.ExpenseTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ExpenseService expenseService;

	@Inject
	private ExpenseTypeService expenseTypeService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private ExpenseQueryService expenseQueryService;

	private BigDecimal expectedValue;

	private Grocery grocery;

	@Before
	public void setup() throws BusinessException {
		final List<Expense> expenses = EntityFactory.gimme(Expense.class, 10, ExpenseTemplate.VALID);
		this.grocery = this.groceryService.createGrocery(this.getUserContext(), expenses.get(0).getGrocery());
		this.expectedValue = BigDecimal.ZERO;
		for (final Expense expense : expenses) {
			this.expenseTypeService.createExpenseType(this.getUserContext(), expense.getExpenseType());
			expense.setGrocery(this.grocery);
			this.expenseService.createExpense(this.getUserContext(), expense);
			this.expectedValue = this.expectedValue.add(expense.getExpenseValue());
		}
	}

	@Test
	public void shouldFindExpensesValueByGroceryAndPeriod() throws BusinessException {
		final LocalDate startDate = LocalDate.now(), endDate = LocalDate.now();

		final BigDecimal expensesValue = this.expenseQueryService.findExpensesValueByGroceryAndPeriod(
				this.grocery.getUuid(),
				startDate,
				endDate);
		Assert.assertNotNull(expensesValue);

		Assert.assertEquals(this.expectedValue.doubleValue(), expensesValue.doubleValue(), 0);
	}

	@Test
	public void shouldFindMonthlyExpensesValueByGroceryAndPeriod() throws BusinessException {
		final LocalDate startDate = LocalDate.now(), endDate = LocalDate.now();

		final List<ExpenseReport> expenses = this.expenseQueryService.findMonthyExpensesByGroceryAndPeriod(
				this.grocery.getUuid(),
				startDate,
				endDate);

		Assert.assertFalse(expenses.isEmpty());
		Assert.assertEquals(1, expenses.size());
		Assert.assertEquals(startDate.getMonth(), expenses.get(0).getMonth());
	}
}
