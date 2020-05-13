package mz.co.grocery.core.expense.service;

import java.util.List;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseTypeQueryService {

	List<ExpenseType> findAllExpensesType(int currentPage, int maxResult) throws BusinessException;

	Long count() throws BusinessException;

	ExpenseType findExpensesTypeByUuid(String expenseTypeUuid) throws BusinessException;

}
