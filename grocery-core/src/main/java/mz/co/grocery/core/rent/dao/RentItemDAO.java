/**
 *
 */
package mz.co.grocery.core.rent.dao;

import mz.co.grocery.core.rent.model.RentItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface RentItemDAO extends GenericDAO<RentItem, Long> {

	class QUERY {
		public static final String fetchByUuid = "SELECT ri FROM RentItem ri LEFT JOIN FETCH ri.stock LEFT JOIN FETCH ri.serviceItem WHERE ri.uuid = :rentItemUuid";
	}

	class QUERY_NAME {
		public static final String fetchByUuid = "RentItem.fetchByUuid";
	}

	RentItem fetchByUuid(String rentItemUuid) throws BusinessException;

}
