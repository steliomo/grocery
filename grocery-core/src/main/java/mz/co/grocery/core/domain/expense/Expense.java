package mz.co.grocery.core.domain.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.unit.Unit;

public class Expense extends Domain {

	private Unit unit;

	private ExpenseType expenseType;

	private LocalDate datePerformed;

	private BigDecimal expenseValue;

	private String description;

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public Optional<ExpenseType> getExpenseType() {
		return Optional.ofNullable(this.expenseType);
	}

	public void setExpenseType(final ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public LocalDate getDatePerformed() {
		return this.datePerformed;
	}

	public void setDatePerformed(final LocalDate datePerformed) {
		this.datePerformed = datePerformed;
	}

	public BigDecimal getExpenseValue() {
		return this.expenseValue;
	}

	public void setExpenseValue(final BigDecimal expenseValue) {
		this.expenseValue = expenseValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
