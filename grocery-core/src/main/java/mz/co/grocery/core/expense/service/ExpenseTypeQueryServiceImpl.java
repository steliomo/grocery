/**
 *
 */
package mz.co.grocery.core.expense.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.dao.ExpenseTypeDAO;
import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ExpenseTypeQueryServiceImpl.NAME)
public class ExpenseTypeQueryServiceImpl implements ExpenseTypeQueryService {

	public static final String NAME = "mz.co.grocery.core.expense.service.ExpenseTypeQueryServiceImpl";

	@Inject
	ExpenseTypeDAO expenseTypeDAO;

	@Override
	public List<ExpenseType> findAllExpensesType(final int currentPage, final int maxResult) throws BusinessException {

		return this.expenseTypeDAO.findAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long count() throws BusinessException {
		return this.expenseTypeDAO.count();
	}

	@Override
	public ExpenseType findExpensesTypeByUuid(final String expenseTypeUuid) throws BusinessException {
		return this.expenseTypeDAO.findByUuid(expenseTypeUuid);
	}
}
