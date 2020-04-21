/**
 *
 */
package mz.co.grocery.integ.resources.expense.dto;

import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class ExpensesDTO {

	private List<ExpenseTypeDTO> expenseTypeDTOs;

	private List<ExpenseDTO> expenseDTOs;

	private Long totalItems;

	public List<ExpenseTypeDTO> getExpenseTypeDTOs() {
		return this.expenseTypeDTOs;
	}

	public ExpensesDTO setExpenseTypeDTOs(final List<ExpenseTypeDTO> expenseTypeDTOs) {
		this.expenseTypeDTOs = expenseTypeDTOs;
		return this;
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
}
