package mz.co.grocery.persistence.expense.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.persistence.expense.repository.ExpenseRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

@NamedQueries({
	@NamedQuery(name = ExpenseRepository.QUERY_NAME.findValueByGroceryAndPeriod, query = ExpenseRepository.QUERY.findValueByGroceryAndPeriod),
	@NamedQuery(name = ExpenseRepository.QUERY_NAME.findMonthlyByGroceryAndPeriod, query = ExpenseRepository.QUERY.findMonthlyByGroceryAndPeriod),
	@NamedQuery(name = ExpenseRepository.QUERY_NAME.findExpensesByUnitAndPeriod, query = ExpenseRepository.QUERY.findExpensesByUnitAndPeriod) })
@Entity
@Table(name = "EXPENSES")
public class ExpenseEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private UnitEntity unit;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXPENSE_TYPE_ID", nullable = false)
	private ExpenseTypeEntity expenseType;

	@Column(name = "DATE_PERFORMED")
	private LocalDate datePerformed;

	@NotNull
	@Column(name = "EXPENSE_VALUE", nullable = false)
	private BigDecimal expenseValue;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false, length = 80)
	private String description;

	public Optional<UnitEntity> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public Optional<ExpenseTypeEntity> getExpenseType() {
		return Optional.ofNullable(this.expenseType);
	}

	public void setExpenseType(final ExpenseTypeEntity expenseType) {
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
