/**
 *
 */
package mz.co.grocery.core.grocery.dao;

import java.util.List;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UnitDetail;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface GroceryUserDAO extends GenericDAO<GroceryUser, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT gu.id FROM GroceryUser gu WHERE gu.entityStatus = :entityStatus";
		public static final String fetchAll = "SELECT gu FROM GroceryUser gu INNER JOIN FETCH gu.grocery WHERE gu.id IN (:groceryUserIds)";
		public static final String fetchByUser = "SELECT gu FROM GroceryUser gu INNER JOIN FETCH gu.grocery WHERE gu.user = :user AND gu.entityStatus = :entityStatus";
		public static final String findUnitDetailByUuid = "SELECT new mz.co.grocery.core.grocery.model.UnitDetail(g.uuid, g.name, COUNT(gu.id), g.balance) FROM GroceryUser gu INNER JOIN gu.grocery g WHERE g.uuid = :unitUuid AND gu.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findAllIds = "GroceryUser.findAllIds";
		public static final String fetchAll = "GroceryUser.fetchAll";
		public static final String fetchByUser = "GroceryUser.fetchByUser";
		public static final String findUnitDetailByUuid = "GroceryUser.findUnitDetailByUuid";

	}

	List<GroceryUser> fetchAllGroceryUsers(int currentPage, int maxResult, EntityStatus entityStatus)
			throws BusinessException;

	GroceryUser fetchByUser(String user, EntityStatus entityStatus) throws BusinessException;

	UnitDetail findUnitDetailByUuid(String unitUuid, EntityStatus entityStatus) throws BusinessException;
}
