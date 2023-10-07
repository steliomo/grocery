/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.util.List;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceItemRepository extends GenericDAO<ServiceItemEntity, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT si.id FROM ServiceItemEntity si where si.entityStatus = :entityStatus";
		public static final String fetchAll = "SELECT si FROM ServiceItemEntity si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit WHERE si.id IN (:serviceItemIds) ORDER BY s.name";
		public static final String fetchByUuid = "SELECT si FROM ServiceItemEntity si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit WHERE si.uuid = :serviceItemUuid";
		public static final String fetchByName = "SELECT si FROM ServiceItemEntity si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit WHERE CONCAT(s.name, ' ', sd.description) LIKE :serviceItemName AND si.entityStatus = :entityStatus";
		public static final String fetchByServiceAndUnit = "SELECT si FROM ServiceItemEntity si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s INNER JOIN FETCH si.unit u WHERE s.uuid = :serviceUuid AND u.uuid  = :unitUuid AND si.entityStatus = :entityStatus ORDER BY s.name";
		public static final String fetchNotInThisUnitByService = "SELECT si FROM ServiceItemEntity si INNER JOIN FETCH si.serviceDescription sd INNER JOIN FETCH sd.service s WHERE NOT EXISTS (SELECT sit FROM ServiceItemEntity sit INNER JOIN sit.unit u INNER JOIN sit.serviceDescription sde INNER JOIN sde.service se WHERE sit.id = si.id AND u.uuid  = :unitUuid ) AND s.uuid = :serviceUuid AND si.entityStatus = :entityStatus GROUP BY sd.id ORDER BY s.name";
	}

	class QUERY_NAME {
		public static final String findAllIds = "ServiceItemEntity.findAllIds";
		public static final String fetchAll = "ServiceItemEntity.fetchAll";
		public static final String fetchByUuid = "ServiceItemEntity.fetchByUuid";
		public static final String fetchByName = "ServiceItemEntity.fetchByName";
		public static final String fetchByServiceAndUnit = "ServiceItemEntity.fetchByServiceAndUnit";
		public static final String fetchNotInThisUnitByService = "ServiceItemEntity.fetchNotInThisUnitByService";
	}

	List<ServiceItemEntity> fetchAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	ServiceItemEntity fetchByUuid(String serviceItemUuid) throws BusinessException;

	List<ServiceItemEntity> fetchByName(String serviceItemName, EntityStatus entityStatus) throws BusinessException;

	List<ServiceItemEntity> fetchByServiceAndUnit(Service service, Unit unit, EntityStatus entityStatus) throws BusinessException;

	List<ServiceItemEntity> fetchNotInThisUnitByService(Service service, Unit unit, EntityStatus entityStatus) throws BusinessException;

}
