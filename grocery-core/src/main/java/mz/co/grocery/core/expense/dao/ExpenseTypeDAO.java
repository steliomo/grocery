package mz.co.grocery.core.expense.dao;

import java.util.List;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseTypeDAO extends GenericDAO<ExpenseType, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT et.id FROM ExpenseType et WHERE et.entityStatus = :entityStatus";
		public static final String findAll = "SELECT et FROM ExpenseType et WHERE et.id IN (:expensesTypeIds) ";
	}

	class QUERY_NAME {
		public static final String findAllIds = "ExpenseType.findAllIds";
		public static final String findAll = "ExpenseType.findAll";
	}

	List<ExpenseType> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;
}
