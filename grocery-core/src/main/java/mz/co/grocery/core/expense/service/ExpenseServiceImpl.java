package mz.co.grocery.core.expense.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.dao.ExpenseDAO;
import mz.co.grocery.core.expense.model.Expense;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ExpenseServiceImpl.NAME)
public class ExpenseServiceImpl extends AbstractService implements ExpenseService {

	public static final String NAME = "mz.co.grocery.core.expense.service.ExpenseServiceImpl";

	@Inject
	private ExpenseDAO expenseDAO;

	@Override
	public Expense createExpense(final UserContext userContext, final Expense expense) throws BusinessException {
		this.expenseDAO.create(userContext, expense);
		return expense;
	}

	@Override
	public List<Expense> registExpenses(final UserContext userContext, final List<Expense> expenses)
			throws BusinessException {

		if (expenses.isEmpty()) {
			throw new BusinessException("Please add at least one expense to be registed!");
		}

		for (final Expense expense : expenses) {
			this.createExpense(userContext, expense);
		}

		return expenses;
	}

}
