/**
 *
 */
package mz.co.grocery.core.domain.expense;

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

	private String name;

	public ExpenseReport(final BigDecimal expenseValue, final LocalDate datePerformed) {
		this.expenseValue = expenseValue;
		this.datePerformed = datePerformed;
	}

	public ExpenseReport(final Month month) {
		this.month = month;
		this.expenseValue = BigDecimal.ZERO;
	}

	public ExpenseReport(final String name, final BigDecimal expenseValue) {
		this.name = name;
		this.expenseValue = expenseValue;
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

	public String getName() {
		return this.name;
	}
}
