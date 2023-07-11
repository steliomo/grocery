package mz.co.grocery.integ.resources.expense.dto;

import mz.co.grocery.core.domain.expense.ExpenseTypeCategory;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseTypeDTO extends GenericDTO {

	private ExpenseTypeCategory expenseTypeCategory;

	private String name;

	private String description;

	public ExpenseTypeCategory getExpenseTypeCategory() {
		return this.expenseTypeCategory;
	}

	public void setExpenseTypeCategory(final ExpenseTypeCategory expenseTypeCategory) {
		this.expenseTypeCategory = expenseTypeCategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
