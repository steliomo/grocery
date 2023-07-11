/**
 *
 */
package mz.co.grocery.persistence.expense.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ExpenseTypeEntityMapper extends AbstractEntityMapper<ExpenseTypeEntity, ExpenseType>
implements EntityMapper<ExpenseTypeEntity, ExpenseType> {

	@Override
	public ExpenseTypeEntity toEntity(final ExpenseType domain) {
		final ExpenseTypeEntity entity = new ExpenseTypeEntity();

		entity.setExpenseTypeCategory(domain.getExpenseTypeCategory());
		entity.setName(domain.getName());
		entity.setDescription(domain.getDescription());

		return this.toEntity(entity, domain);
	}

	@Override
	public ExpenseType toDomain(final ExpenseTypeEntity entity) {
		final ExpenseType domain = new ExpenseType();

		domain.setExpenseTypeCategory(entity.getExpenseTypeCategory());
		domain.setName(entity.getName());
		domain.setDescription(entity.getDescription());

		return this.toDomain(entity, domain);
	}
}
