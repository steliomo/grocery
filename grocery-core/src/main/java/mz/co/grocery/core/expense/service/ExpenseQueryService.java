/**
 *
 */
package mz.co.grocery.core.expense.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseQueryService {

	BigDecimal findExpensesValueByGroceryAndPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate)
			throws BusinessException;

	List<ExpenseReport> findMonthyExpensesByGroceryAndPeriod(String groceryUuid, LocalDate startDate,
			LocalDate endDate) throws BusinessException;

	List<ExpenseReport> findExpensesByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate) throws BusinessException;
}
