package mz.co.grocery.core.expense.service;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseTypeService {

	ExpenseType createExpenseType(UserContext userContext, ExpenseType expenseType) throws BusinessException;

	ExpenseType updateExpenseType(UserContext userContext, ExpenseType expenseType) throws BusinessException;

}
