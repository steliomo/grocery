package mz.co.grocery.core.application.expense.in;

import java.util.List;

import mz.co.grocery.core.domain.expense.Expense;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RegistExpenseUseCase {

	List<Expense> registExpenses(UserContext userContext, List<Expense> expenses) throws BusinessException;

}
