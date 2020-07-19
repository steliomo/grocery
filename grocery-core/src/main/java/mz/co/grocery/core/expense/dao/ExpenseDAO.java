package mz.co.grocery.core.expense.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.grocery.core.expense.model.ExpenseTypeCategory;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseDAO extends GenericDAO<Expense, Long> {

	class QUERY {
		public static final String findValueByGroceryAndPeriod = "SELECT SUM(e.expenseValue) FROM Expense e WHERE e.grocery.uuid = :groceryUuid AND e.datePerformed BETWEEN :startDate AND :endDate AND e.expenseType.expenseTypeCategory = :expenseTypeCategory AND e.entityStatus = :entityStatus";

		public static final String findMonthlyByGroceryAndPeriod = "SELECT new mz.co.grocery.core.expense.model.ExpenseReport(SUM(e.expenseValue), e.datePerformed) FROM Expense e WHERE e.grocery.uuid = :groceryUuid AND e.datePerformed BETWEEN :startDate AND :endDate AND e.expenseType.expenseTypeCategory = :expenseTypeCategory AND e.entityStatus = :entityStatus"
				+ " GROUP BY MONTH(e.datePerformed) ORDER BY MONTH(e.datePerformed) ASC";
	}

	class QUERY_NAME {

		public static final String findValueByGroceryAndPeriod = "Expense.findValueByGroceryAndPeriod";

		public static final String findMonthlyByGroceryAndPeriod = "Expense.findMonthlyByGroceryAndPeriod";

	}

	BigDecimal findValueByGroceryAndPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			ExpenseTypeCategory expenseTypeCategory,
			EntityStatus entityStatus)
					throws BusinessException;

	List<ExpenseReport> findMonthlyByGroceryAndPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			ExpenseTypeCategory expenseTypeCategory,
			EntityStatus entityStatus) throws BusinessException;
}
