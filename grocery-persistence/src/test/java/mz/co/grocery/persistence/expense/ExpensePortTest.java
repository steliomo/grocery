/**
 *
 */
package mz.co.grocery.persistence.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.expense.in.ExpenseTypePort;
import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseReport;
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
public class ExpensePortTest extends AbstractIntegServiceTest {

	@Inject
	private ExpensePort expensePort;

	@Inject
	private ExpenseTypePort expenseTypePort;

	@Inject
	private UnitPort unitPort;

	private BigDecimal expectedValue;

	private Unit unit;

	private LocalDate startDate;

	private LocalDate endDate;

	@Before
	public void setup() throws BusinessException {
		this.startDate = LocalDate.now();
		this.endDate = LocalDate.now();

		final List<Expense> expenses = EntityFactory.gimme(Expense.class, 10, ExpenseTemplate.VALID);
		this.unit = this.unitPort.createUnit(this.getUserContext(), expenses.get(0).getUnit().get());
		this.expectedValue = BigDecimal.ZERO;

		for (final Expense expense : expenses) {
			final ExpenseType expenseType = this.expenseTypePort.createExpenseType(this.getUserContext(), expense.getExpenseType().get());

			expense.setUnit(this.unit);
			expense.setExpenseType(expenseType);

			this.expensePort.createExpense(this.getUserContext(), expense);

			this.expectedValue = this.expectedValue.add(expense.getExpenseValue());
		}
	}

	@Test
	public void shouldFindExpensesValueByUnitAndPeriod() throws BusinessException {

		final BigDecimal expensesValue = this.expensePort
				.findExpensesValueByUnitAndPeriod(
						this.unit.getUuid(),
						this.startDate,
						this.endDate);

		Assert.assertNotNull(expensesValue);

		Assert.assertEquals(this.expectedValue.doubleValue(), expensesValue.doubleValue(), 0);
	}

	@Test
	public void shouldFindMonthlyExpensesValueByGroceryAndPeriod() throws BusinessException {

		final List<ExpenseReport> expenses = this.expensePort.findMonthyExpensesByGroceryAndPeriod(
				this.unit.getUuid(),
				this.startDate,
				this.endDate);

		Assert.assertFalse(expenses.isEmpty());
		Assert.assertEquals(1, expenses.size());
		Assert.assertEquals(this.startDate.getMonth(), expenses.get(0).getMonth());
	}

	@Test
	public void shouldFindExpensesByUnitAndPeriod() throws BusinessException {

		final List<ExpenseReport> expenses = this.expensePort.findExpensesByUnitAndPeriod(this.unit.getUuid(), this.startDate,
				this.endDate);

		Assert.assertFalse(expenses.isEmpty());
	}
}
