/**
 *
 */
package mz.co.grocery.persistence.expense.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ExpenseEntityMapper extends AbstractEntityMapper<ExpenseEntity, Expense> implements EntityMapper<ExpenseEntity, Expense> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<ExpenseTypeEntity, ExpenseType> expenseTypeMapper;

	public ExpenseEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper, final EntityMapper<ExpenseTypeEntity, ExpenseType> expenseTypeMapper) {
		this.unitMapper = unitMapper;
		this.expenseTypeMapper = expenseTypeMapper;
	}

	@Override
	public ExpenseEntity toEntity(final Expense domain) {
		final ExpenseEntity entity = new ExpenseEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getExpenseType().ifPresent(expenseType -> entity.setExpenseType(this.expenseTypeMapper.toEntity(expenseType)));

		entity.setDatePerformed(domain.getDatePerformed());
		entity.setExpenseValue(domain.getExpenseValue());
		entity.setDescription(domain.getDescription());

		return this.toEntity(entity, domain);
	}

	@Override
	public Expense toDomain(final ExpenseEntity entity) {
		final Expense domain = new Expense();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		entity.getExpenseType().ifPresent(expenseType -> domain.setExpenseType(this.expenseTypeMapper.toDomain(expenseType)));

		domain.setDatePerformed(entity.getDatePerformed());
		domain.setExpenseValue(entity.getExpenseValue());
		domain.setDescription(entity.getDescription());

		return this.toDomain(entity, domain);
	}
}
