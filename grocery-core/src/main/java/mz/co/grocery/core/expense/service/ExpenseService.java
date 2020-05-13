package mz.co.grocery.core.expense.service;

import java.util.List;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseService {

	Expense createExpense(UserContext userContext, Expense expense) throws BusinessException;

	List<Expense> registExpenses(UserContext userContext, List<Expense> expenses) throws BusinessException;

}
