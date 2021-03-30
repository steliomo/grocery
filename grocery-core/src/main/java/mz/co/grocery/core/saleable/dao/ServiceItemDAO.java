/**
 *
 */
package mz.co.grocery.core.saleable.dao;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.Service;
import mz.co.grocery.core.saleable.model.ServiceItem;
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
		public static final String fetchByName = "SELECT si FROM ServiceItem si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit WHERE CONCAT(s.name, ' ', sd.description) LIKE :serviceItemName AND si.entityStatus = :entityStatus";
		public static final String fetchByServiceAndUnit = "SELECT si FROM ServiceItem si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit u WHERE s.uuid = :serviceUuid AND u.uuid  = :unitUuid AND si.entityStatus = :entityStatus ORDER BY s.name";
		public static final String fetchNotInThisUnitByService = "SELECT si FROM ServiceItem si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s WHERE NOT EXISTS (SELECT sit FROM ServiceItem sit INNER JOIN sit.unit u INNER JOIN sit.serviceDescription sde INNER JOIN sde.service se WHERE sit.id = si.id AND u.uuid  = :unitUuid ) AND s.uuid = :serviceUuid AND si.entityStatus = :entityStatus GROUP BY sd.id ORDER BY s.name";
	}

	class QUERY_NAME {
		public static final String findAllIds = "ServiceItem.findAllIds";
		public static final String fetchAll = "ServiceItem.fetchAll";
		public static final String fetchByUuid = "ServiceItem.fetchByUuid";
		public static final String fetchByName = "ServiceItem.fetchByName";
		public static final String fetchByServiceAndUnit = "ServiceItem.fetchByServiceAndUnit";
		public static final String fetchNotInThisUnitByService = "ServiceItem.fetchNotInThisUnitByService";
	}

	List<ServiceItem> fetchAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	ServiceItem fetchByUuid(String serviceItemUuid) throws BusinessException;

	List<ServiceItem> fetchByName(String serviceItemName, EntityStatus entityStatus) throws BusinessException;

	List<ServiceItem> fetchByServiceAndUnit(Service service, Grocery unit, EntityStatus entityStatus) throws BusinessException;

	List<ServiceItem> fetchNotInThisUnitByService(Service service, Grocery unit, EntityStatus entityStatus) throws BusinessException;

}
