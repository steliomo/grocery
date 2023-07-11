/**
 *
 */
package mz.co.grocery.integ.resources.expense.dto;

import java.math.BigDecimal;
import java.util.List;

import mz.co.grocery.core.domain.expense.ExpenseReport;

/**
 * @author Stélio Moiane
 *
 */
public class ExpensesDTO {

	private List<ExpenseTypeDTO> expenseTypeDTOs;

	private List<ExpenseDTO> expenseDTOs;

	private List<ExpenseReport> expensesReport;

	private Long totalItems;

	private BigDecimal totalValue;

	public List<ExpenseTypeDTO> getExpenseTypeDTOs() {
		return this.expenseTypeDTOs;
	}

	public ExpensesDTO setExpenseTypeDTOs(final List<ExpenseTypeDTO> expenseTypeDTOs) {
		this.expenseTypeDTOs = expenseTypeDTOs;
		return this;
	}

	public void setExpensesReport(final List<ExpenseReport> expensesReport) {
		this.expensesReport = expensesReport;
		this.totalValue = expensesReport.stream().map(ExpenseReport::getExpenseValue).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}

	public ExpensesDTO setTotalItems(final Long totalItems) {
		this.totalItems = totalItems;
		return this;
	}

	public List<ExpenseDTO> getExpenseDTOs() {
		return this.expenseDTOs;
	}

	public ExpensesDTO setExpenseDTOs(final List<ExpenseDTO> expenseDTOs) {
		this.expenseDTOs = expenseDTOs;
		return this;
	}

	public List<ExpenseReport> getExpensesReport() {
		return this.expensesReport;
	}

	public BigDecimal getTotalValue() {
		return this.totalValue;
	}
}
