/**
 *
 */
package mz.co.grocery.core.expense.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseReport {

	private final BigDecimal expenseValue;

	private LocalDate datePerformed;

	private Month month;

	public ExpenseReport(final BigDecimal expenseValue, final LocalDate datePerformed) {
		this.expenseValue = expenseValue;
		this.datePerformed = datePerformed;
	}

	public ExpenseReport(final Month month) {
		this.month = month;
		this.expenseValue = BigDecimal.ZERO;
	}

	public BigDecimal getExpenseValue() {
		return this.expenseValue;
	}

	public LocalDate getDatePerformed() {
		return this.datePerformed;
	}

	public Month getMonth() {

		if (this.datePerformed != null) {
			return this.datePerformed.getMonth();
		}

		return this.month;
	}
}
