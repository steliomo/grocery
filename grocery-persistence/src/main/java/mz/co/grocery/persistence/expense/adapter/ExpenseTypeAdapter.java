package mz.co.grocery.persistence.expense.adapter;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.expense.in.ExpenseTypePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.persistence.expense.entity.ExpenseTypeEntity;
import mz.co.grocery.persistence.expense.repository.ExpenseTypeRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class ExpenseTypeAdapter implements ExpenseTypePort {

	private ExpenseTypeRepository repository;

	private EntityMapper<ExpenseTypeEntity, ExpenseType> mapper;

	public ExpenseTypeAdapter(final ExpenseTypeRepository repository,
			final EntityMapper<ExpenseTypeEntity, ExpenseType> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public ExpenseType createExpenseType(final UserContext userContext, final ExpenseType expenseType)
			throws BusinessException {
		final ExpenseTypeEntity entity = this.mapper.toEntity(expenseType);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public ExpenseType updateExpenseType(final UserContext userContext, final ExpenseType expenseType) throws BusinessException {

		final ExpenseTypeEntity entity = this.mapper.toEntity(expenseType);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<ExpenseType> findAllExpensesType(final int currentPage, final int maxResult) throws BusinessException {

		return this.repository.findAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long count() throws BusinessException {
		return this.repository.count();
	}

	@Override
	public ExpenseType findExpensesTypeByUuid(final String expenseTypeUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(expenseTypeUuid));
	}
}
