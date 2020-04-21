package mz.co.grocery.core.expense.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

@Repository(ExpenseTypeDAOImpl.NAME)
public class ExpenseTypeDAOImpl extends GenericDAOImpl<ExpenseType, Long> implements ExpenseTypeDAO {

	public static final String NAME = "mz.co.grocery.core.expense.dao.ExpenseTypeDAOImpl";

	@Override
	public List<ExpenseType> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {

		final List<Long> expensesTypeIds = this
				.findByQuery(ExpenseTypeDAO.QUERY_NAME.findAllIds, new ParamBuilder().add("entityStatus", entityStatus)
						.process(), Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (expensesTypeIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(ExpenseTypeDAO.QUERY_NAME.findAll,
				new ParamBuilder().add("expensesTypeIds", expensesTypeIds).process());
	}

}
