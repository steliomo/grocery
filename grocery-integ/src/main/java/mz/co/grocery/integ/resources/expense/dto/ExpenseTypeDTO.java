package mz.co.grocery.integ.resources.expense.dto;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseTypeDTO extends GenericDTO<ExpenseType> {

	private String name;

	private String description;

	public ExpenseTypeDTO() {
	}

	public ExpenseTypeDTO(final ExpenseType expenseType) {
		super(expenseType);
		this.mapper(expenseType);
	}

	@Override
	public void mapper(final ExpenseType expenseType) {
		this.name = expenseType.getName();
		this.description = expenseType.getDescription();
	}

	@Override
	public ExpenseType get() {
		final ExpenseType expenseType = this.get(new ExpenseType());
		expenseType.setName(this.name);
		expenseType.setDescription(this.description);
		return expenseType;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}
}
