package mz.co.grocery.core.expense.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.expense.dao.ExpenseTypeDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

@NamedQueries({ @NamedQuery(name = ExpenseTypeDAO.QUERY_NAME.findAllIds, query = ExpenseTypeDAO.QUERY.findAllIds),
	@NamedQuery(name = ExpenseTypeDAO.QUERY_NAME.findAll, query = ExpenseTypeDAO.QUERY.findAll) })

@Entity
@Table(name = "EXPENSES_TYPE")
public class ExpenseType extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "EXPENSE_TYPE_CATEGORY", nullable = false, length = 50)
	private ExpenseTypeCategory expenseTypeCategory;

	@NotEmpty
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false, length = 80)
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
