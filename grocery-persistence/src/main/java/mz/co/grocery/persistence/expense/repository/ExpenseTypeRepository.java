package mz.co.grocery.persistence.expense.repository;

import java.util.List;

import mz.co.grocery.persistence.expense.entity.ExpenseTypeEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ExpenseTypeRepository extends GenericDAO<ExpenseTypeEntity, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT et.id FROM ExpenseTypeEntity et WHERE et.entityStatus = :entityStatus";
		public static final String findAll = "SELECT et FROM ExpenseTypeEntity et WHERE et.id IN (:expensesTypeIds) ";
	}

	class QUERY_NAME {
		public static final String findAllIds = "ExpenseTypeEntity.findAllIds";
		public static final String findAll = "ExpenseTypeEntity.findAll";
	}

	List<ExpenseTypeEntity> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;
}
