package mz.co.grocery.core.expense.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

@Repository(ExpenseDAOImpl.NAME)
public class ExpenseDAOImpl extends GenericDAOImpl<Expense, Long> implements ExpenseDAO {

	public static final String NAME = "mz.co.grocery.core.expense.dao.ExpenseDAOImpl";

}
