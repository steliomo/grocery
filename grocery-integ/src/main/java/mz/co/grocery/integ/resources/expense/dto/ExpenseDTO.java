/**
 *
 */
package mz.co.grocery.integ.resources.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseDTO extends GenericDTO<Expense> {

	private GroceryDTO groceryDTO;

	private ExpenseTypeDTO expenseTypeDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate datePerformed;

	private BigDecimal expenseValue;

	private String description;

	public ExpenseDTO() {
	}

	public ExpenseDTO(final Expense expense) {
		super(expense);
		this.mapper(expense);
	}

	@Override
	public void mapper(final Expense expense) {
		this.groceryDTO = new GroceryDTO(expense.getGrocery());
		this.expenseTypeDTO = new ExpenseTypeDTO(expense.getExpenseType());
		this.datePerformed = expense.getDatePerformed();
		this.expenseValue = expense.getExpenseValue();
		this.description = expense.getDescription();
	}

	@Override
	public Expense get() {
		final Expense expense = this.get(new Expense());
		expense.setGrocery(this.groceryDTO.get());
		expense.setExpenseType(this.expenseTypeDTO.get());
		expense.setDatePerformed(this.datePerformed);
		expense.setExpenseValue(this.expenseValue);
		expense.setDescription(this.description);

		return expense;
	}

	public GroceryDTO getGroceryDTO() {
		return this.groceryDTO;
	}

	public ExpenseTypeDTO getExpenseTypeDTO() {
		return this.expenseTypeDTO;
	}

	public LocalDate getDatePerformed() {
		return this.datePerformed;
	}

	public BigDecimal getExpenseValue() {
		return this.expenseValue;
	}

	public String getDescription() {
		return this.description;
	}
}
