package mz.co.grocery.persistence.expense.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.expense.entity.ExpenseTypeEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

@Repository
public class ExpenseTypeRepositoryImpl extends GenericDAOImpl<ExpenseTypeEntity, Long> implements ExpenseTypeRepository {

	@Override
	public List<ExpenseTypeEntity> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {

		final List<Long> expensesTypeIds = this
				.findByQuery(ExpenseTypeRepository.QUERY_NAME.findAllIds, new ParamBuilder().add("entityStatus", entityStatus)
						.process(), Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (expensesTypeIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(ExpenseTypeRepository.QUERY_NAME.findAll,
				new ParamBuilder().add("expensesTypeIds", expensesTypeIds).process());
	}
}
