package mz.co.grocery.core.application.expense.in;

import java.util.List;

import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseTypePort {

	ExpenseType createExpenseType(UserContext userContext, ExpenseType expenseType) throws BusinessException;

	ExpenseType updateExpenseType(UserContext userContext, ExpenseType expenseType) throws BusinessException;

	List<ExpenseType> findAllExpensesType(int currentPage, int maxResult) throws BusinessException;

	Long count() throws BusinessException;

	ExpenseType findExpensesTypeByUuid(String expenseTypeUuid) throws BusinessException;

}
