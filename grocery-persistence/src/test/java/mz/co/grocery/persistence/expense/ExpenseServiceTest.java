package mz.co.grocery.persistence.expense;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.core.expense.service.ExpenseService;
import mz.co.grocery.core.expense.service.ExpenseTypeService;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ExpenseTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ExpenseService expenseService;

	@Inject
	private ExpenseTypeService expenseTypeService;

	@Inject
	private GroceryService groceryService;

	private Expense expense;

	@Before
	public void setup() throws BusinessException {
		this.expense = EntityFactory.gimme(Expense.class, ExpenseTemplate.VALID);
		this.expenseTypeService.createExpenseType(this.getUserContext(), this.expense.getExpenseType());
		this.groceryService.createGrocery(this.getUserContext(), this.expense.getGrocery());
	}

	@Test
	public void shouldCreateExpense() throws BusinessException {

		this.expenseService.createExpense(this.getUserContext(), this.expense);

		TestUtil.assertCreation(this.expense);
	}
}
