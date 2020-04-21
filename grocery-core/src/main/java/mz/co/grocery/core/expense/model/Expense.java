package mz.co.grocery.core.expense.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

@Entity
@Table(name = "EXPENSES")
public class Expense extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private Grocery grocery;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXPENSE_TYPE_ID", nullable = false)
	private ExpenseType expenseType;

	@Column(name = "DATE_PERFORMED")
	private LocalDate datePerformed;

	@NotNull
	@Column(name = "EXPENSE_VALUE", nullable = false)
	private BigDecimal expenseValue;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false, length = 80)
	private String description;

	public Grocery getGrocery() {
		return this.grocery;
	}

	public void setGrocery(final Grocery grocery) {
		this.grocery = grocery;
	}

	public ExpenseType getExpenseType() {
		return this.expenseType;
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
