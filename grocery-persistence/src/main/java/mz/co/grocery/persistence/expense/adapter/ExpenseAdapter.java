/**
 *
 */
package mz.co.grocery.persistence.expense.adapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import mz.co.grocery.core.application.expense.out.ExpensePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseReport;
import mz.co.grocery.core.domain.expense.ExpenseTypeCategory;
import mz.co.grocery.persistence.expense.entity.ExpenseEntity;
import mz.co.grocery.persistence.expense.repository.ExpenseRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class ExpenseAdapter implements ExpensePort {

	private ExpenseRepository repository;

	private EntityMapper<ExpenseEntity, Expense> mapper;

	public ExpenseAdapter(final ExpenseRepository repository,
			final EntityMapper<ExpenseEntity, Expense> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public BigDecimal findExpensesValueByUnitAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate) throws BusinessException {

		final BigDecimal expenses = this.repository.findValueByGroceryAndPeriod(groceryUuid, startDate, endDate,
				ExpenseTypeCategory.EXPENSE,
				EntityStatus.ACTIVE);

		if (expenses == null) {
			return BigDecimal.ZERO;
		}

		return expenses;
	}

	@Override
	public List<ExpenseReport> findMonthyExpensesByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate) throws BusinessException {

		return this.repository.findMonthlyByGroceryAndPeriod(groceryUuid, startDate, endDate,
				ExpenseTypeCategory.EXPENSE, EntityStatus.ACTIVE);
	}

	@Override
	public List<ExpenseReport> findExpensesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate)
			throws BusinessException {
		return this.repository.findExpensesByUnitAndPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Transactional
	@Override
	public Expense createExpense(final UserContext userContext, final Expense expense) throws BusinessException {

		final ExpenseEntity entity = this.mapper.toEntity(expense);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}
}
