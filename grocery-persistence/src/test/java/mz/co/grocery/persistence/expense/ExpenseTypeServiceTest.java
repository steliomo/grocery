package mz.co.grocery.persistence.expense;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.grocery.core.expense.service.ExpenseTypeService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ExpenseTypeTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseTypeServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ExpenseTypeService expenseTypeService;

	private ExpenseType expenseType;

	@Before
	public void setup() {
		this.expenseType = EntityFactory.gimme(ExpenseType.class, ExpenseTypeTemplate.VALID);
	}

	@Test
	public void shouldCreateExpense() throws BusinessException {

		this.expenseTypeService.createExpenseType(this.getUserContext(), this.expenseType);

		TestUtil.assertCreation(this.expenseType);
	}

	@Test
	public void shouldUpdateExpenseType() throws BusinessException {
		this.expenseTypeService.createExpenseType(this.getUserContext(), this.expenseType);
		this.expenseTypeService.updateExpenseType(this.getUserContext(), this.expenseType);

		TestUtil.assertUpdate(this.expenseType);
	}
}
