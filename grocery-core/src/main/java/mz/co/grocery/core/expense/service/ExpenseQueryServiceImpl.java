/**
 *
 */
package mz.co.grocery.core.expense.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.dao.ExpenseDAO;
import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.grocery.core.expense.model.ExpenseTypeCategory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ExpenseQueryServiceImpl.NAME)
public class ExpenseQueryServiceImpl implements ExpenseQueryService {

	public static final String NAME = "mz.co.grocery.core.expense.service.ExpenseQueryServiceImpl";

	@Inject
	ExpenseDAO expenseDAO;

	@Override
	public BigDecimal findExpensesValueByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate) throws BusinessException {

		final BigDecimal expenses = this.expenseDAO.findValueByGroceryAndPeriod(groceryUuid, startDate, endDate,
				ExpenseTypeCategory.EXPENSE,
				EntityStatus.ACTIVE);

		if (expenses == null) {
			return BigDecimal.ZERO;
		}

		return expenses;
	}

	@Override
	public List<ExpenseReport> findMonthyExpensesByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate) throws BusinessException {

		return this.expenseDAO.findMonthlyByGroceryAndPeriod(groceryUuid, startDate, endDate,
				ExpenseTypeCategory.EXPENSE, EntityStatus.ACTIVE);
	}

}
