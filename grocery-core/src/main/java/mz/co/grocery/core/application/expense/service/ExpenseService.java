package mz.co.grocery.core.application.expense.service;

import java.util.List;

import mz.co.grocery.core.application.expense.in.RegistExpenseUseCase;
import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.expense.Expense;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class ExpenseService extends AbstractService implements RegistExpenseUseCase {

	private ExpensePort expensePort;

	public ExpenseService(final ExpensePort expensePort) {
		this.expensePort = expensePort;
	}

	@Override
	public List<Expense> registExpenses(final UserContext userContext, final List<Expense> expenses)
			throws BusinessException {

		if (expenses.isEmpty()) {
			throw new BusinessException("add.at.least.one.expense.to.be.registed");
		}

		for (final Expense expense : expenses) {
			this.expensePort.createExpense(userContext, expense);
		}

		return expenses;
	}
}
