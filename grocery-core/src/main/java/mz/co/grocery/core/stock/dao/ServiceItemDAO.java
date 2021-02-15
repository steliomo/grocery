/**
 *
 */
package mz.co.grocery.core.stock.dao;

import java.util.List;

import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceItemDAO extends GenericDAO<ServiceItem, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT si.id FROM ServiceItem si where si.entityStatus = :entityStatus";
		public static final String fetchAll = "SELECT si FROM ServiceItem si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit WHERE si.id IN (:serviceItemIds) ORDER BY s.name";
		public static final String fetchByUuid = "SELECT si FROM ServiceItem si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit WHERE si.uuid = :serviceItemUuid";
	}

	class QUERY_NAME {
		public static final String findAllIds = "ServiceItem.findAllIds";
		public static final String fetchAll = "ServiceItem.fetchAll";
		public static final String fetchByUuid = "ServiceItem.fetchByUuid";
	}

	List<ServiceItem> fetchAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	ServiceItem fetchByUuid(String serviceItemUuid) throws BusinessException;

}
