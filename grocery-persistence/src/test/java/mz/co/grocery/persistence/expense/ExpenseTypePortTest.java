package mz.co.grocery.persistence.expense;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.expense.in.ExpenseTypePort;
import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ExpenseTypeTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseTypePortTest extends AbstractIntegServiceTest {

	@Inject
	private ExpenseTypePort expenseTypePort;

	private String lastUuid;

	@Before
	public void setup() {
		final List<ExpenseType> expensesType = EntityFactory.gimme(ExpenseType.class, 10, ExpenseTypeTemplate.VALID);
		expensesType.forEach(expenseType -> {
			this.createExpenseType(expenseType);
		});
	}

	private void createExpenseType(ExpenseType expenseType) {
		try {
			expenseType = this.expenseTypePort.createExpenseType(this.getUserContext(), expenseType);
			this.lastUuid = expenseType.getUuid();
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFindExpensesType() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<ExpenseType> expensesType = this.expenseTypePort.findAllExpensesType(currentPage, maxResult);

		Assert.assertFalse(expensesType.isEmpty());
		Assert.assertEquals(maxResult, expensesType.size());
	}

	@Test
	public void shouldFindExpenseTypeByUuid() throws BusinessException {
		final ExpenseType expesneType = this.expenseTypePort.findExpensesTypeByUuid(this.lastUuid);
		Assert.assertEquals(this.lastUuid, expesneType.getUuid());
	}

	@Test
	public void shouldCreateExpense() throws BusinessException {
		final ExpenseType expenseType = EntityFactory.gimme(ExpenseType.class, ExpenseTypeTemplate.VALID);

		this.expenseTypePort.createExpenseType(this.getUserContext(), expenseType);
	}

	@Test
	public void shouldUpdateExpenseType() throws BusinessException {
		ExpenseType expenseType = EntityFactory.gimme(ExpenseType.class, ExpenseTypeTemplate.VALID);

		expenseType = this.expenseTypePort.createExpenseType(this.getUserContext(), expenseType);
		this.expenseTypePort.updateExpenseType(this.getUserContext(), expenseType);
	}
}
