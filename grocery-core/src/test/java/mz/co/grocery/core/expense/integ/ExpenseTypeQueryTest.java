package mz.co.grocery.core.expense.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.grocery.core.expense.service.ExpenseTypeQueryService;
import mz.co.grocery.core.expense.service.ExpenseTypeService;
import mz.co.grocery.core.fixturefactory.ExpenseTypeTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseTypeQueryTest extends AbstractIntegServiceTest {

	@Inject
	private ExpenseTypeService expenseTypeService;

	@Inject
	private ExpenseTypeQueryService expenseTypeQueryService;

	private String lastUuid;

	@Before
	public void setup() {
		final List<ExpenseType> expensesType = EntityFactory.gimme(ExpenseType.class, 10, ExpenseTypeTemplate.VALID);
		expensesType.forEach(expenseType -> {
			this.createExpenseType(expenseType);
		});
	}

	private void createExpenseType(final ExpenseType expenseType) {
		try {
			this.expenseTypeService.createExpenseType(this.getUserContext(), expenseType);
			this.lastUuid = expenseType.getUuid();
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFindExpensesType() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<ExpenseType> expensesType = this.expenseTypeQueryService.findAllExpensesType(currentPage, maxResult);

		Assert.assertFalse(expensesType.isEmpty());
		Assert.assertEquals(maxResult, expensesType.size());
	}

	@Test
	public void shouldFindExpenseTypeByUuid() throws BusinessException {
		final ExpenseType expesneType = this.expenseTypeQueryService.findExpensesTypeByUuid(this.lastUuid);
		Assert.assertEquals(this.lastUuid, expesneType.getUuid());
	}
}
