/**
 *
 */
package mz.co.grocery.core.grocery.dao;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface GroceryDAO extends GenericDAO<Grocery, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT g.id FROM Grocery g WHERE g.entityStatus = :entityStatus";
		public static final String findAll = "SELECT g FROM Grocery g WHERE g.id IN (:groceryIds)";
		public static final String findByName = "SELECT g FROM Grocery g WHERE g.name LIKE :unitName AND g.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findAllIds = "Grocery.findAllIds";
		public static final String findAll = "Grocery.findAll";
		public static final String findByName = "Grocery.findByName";
	}

	List<Grocery> findAll(int currentPage, int maxResul, EntityStatus entityStatus) throws BusinessException;

	List<Grocery> findByName(String unitName, EntityStatus entityStatus) throws BusinessException;
}
