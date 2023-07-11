/**
 *
 */
package mz.co.grocery.core.application.expense.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpensePort {

	Expense createExpense(UserContext userContext, Expense expense) throws BusinessException;

	BigDecimal findExpensesValueByUnitAndPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate)
			throws BusinessException;

	List<ExpenseReport> findMonthyExpensesByGroceryAndPeriod(String groceryUuid, LocalDate startDate,
			LocalDate endDate) throws BusinessException;

	List<ExpenseReport> findExpensesByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate) throws BusinessException;
}
