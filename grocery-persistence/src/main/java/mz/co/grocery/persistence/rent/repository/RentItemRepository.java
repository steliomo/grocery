/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import mz.co.grocery.persistence.rent.entity.RentItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface RentItemRepository extends GenericDAO<RentItemEntity, Long> {

	class QUERY {
		public static final String fetchByUuid = "SELECT ri FROM RentItemEntity ri LEFT JOIN FETCH ri.stock LEFT JOIN FETCH ri.serviceItem WHERE ri.uuid = :rentItemUuid";
	}

	class QUERY_NAME {
		public static final String fetchByUuid = "RentItemEntity.fetchByUuid";
	}

	RentItemEntity fetchByUuid(String rentItemUuid) throws BusinessException;

}
