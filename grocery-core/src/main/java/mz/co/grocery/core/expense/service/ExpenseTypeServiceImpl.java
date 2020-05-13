package mz.co.grocery.core.expense.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.dao.ExpenseTypeDAO;
import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ExpenseTypeServiceImpl.NAME)
public class ExpenseTypeServiceImpl extends AbstractService implements ExpenseTypeService {

	public static final String NAME = "mz.co.grocery.core.expense.service.ExpenseTypeServiceImpl";

	@Inject
	private ExpenseTypeDAO expenseTypeDAO;

	@Override
	public ExpenseType createExpenseType(final UserContext userContext, final ExpenseType expenseType)
			throws BusinessException {
		this.expenseTypeDAO.create(userContext, expenseType);
		return expenseType;
	}

	@Override
	public ExpenseType updateExpenseType(final UserContext userContext, final ExpenseType expenseType) throws BusinessException {
		this.expenseTypeDAO.update(userContext, expenseType);
		return expenseType;
	}
}
