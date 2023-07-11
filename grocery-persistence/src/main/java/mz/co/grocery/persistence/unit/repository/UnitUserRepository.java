/**
 *
 */
package mz.co.grocery.persistence.unit.repository;

import java.util.List;

import mz.co.grocery.core.domain.unit.UnitDetail;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.persistence.unit.entity.UnitUserEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface UnitUserRepository extends GenericDAO<UnitUserEntity, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT gu.id FROM UnitUserEntity gu WHERE gu.entityStatus = :entityStatus";
		public static final String fetchAll = "SELECT gu FROM UnitUserEntity gu INNER JOIN FETCH gu.unit WHERE gu.id IN (:groceryUserIds)";
		public static final String fetchByUser = "SELECT gu FROM UnitUserEntity gu INNER JOIN FETCH gu.unit WHERE gu.user = :user AND gu.entityStatus = :entityStatus";
		public static final String findUnitDetailByUuid = "SELECT NEW mz.co.grocery.core.domain.unit.UnitDetail(g.uuid, g.name, COUNT(gu.id), g.balance) FROM UnitUserEntity gu INNER JOIN gu.unit g WHERE g.uuid = :unitUuid AND gu.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findAllIds = "UnitUserEntity.findAllIds";
		public static final String fetchAll = "UnitUserEntity.fetchAll";
		public static final String fetchByUser = "UnitUserEntity.fetchByUser";
		public static final String findUnitDetailByUuid = "UnitUserEntity.findUnitDetailByUuid";

	}

	List<UnitUser> fetchAllGroceryUsers(int currentPage, int maxResult, EntityStatus entityStatus)
			throws BusinessException;

	UnitUser fetchByUser(String user, EntityStatus entityStatus) throws BusinessException;

	UnitDetail findUnitDetailByUuid(String unitUuid, EntityStatus entityStatus) throws BusinessException;
}
