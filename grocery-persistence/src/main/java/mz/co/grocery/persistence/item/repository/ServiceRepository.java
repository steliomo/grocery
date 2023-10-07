/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ServiceEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceRepository extends GenericDAO<ServiceEntity, Long> {

	class QUERY {
		public static final String findAll = "SELECT s FROM ServiceEntity s WHERE s.entityStatus = :entityStatus ORDER BY s.name";
		public static final String findByName = "SELECT s FROM ServiceEntity s WHERE s.entityStatus = :entityStatus AND s.name LIKE :serviceName ORDER BY s.name";
		public static final String findByUnit = "SELECT s FROM ServiceItemEntity si INNER JOIN si.serviceDescription sd INNER JOIN sd.service s INNER JOIN si.unit u WHERE u.uuid = :unitUuid AND si.entityStatus = :entityStatus GROUP BY s.id ORDER BY s.name";
		public static final String findNotInThisUnit = "SELECT s FROM ServiceItemEntity si INNER JOIN si.serviceDescription sd INNER JOIN sd.service s WHERE NOT EXISTS "
				+ "( SELECT sde FROM ServiceItemEntity sit INNER JOIN sit.serviceDescription sde INNER JOIN sit.unit u WHERE u.uuid = :unitUuid AND sde.id = sd.id) AND s.entityStatus = :entityStatus GROUP BY s.id ORDER BY s.name";
	}

	class QUERY_NAME {

		public static final String findAll = "ServiceEntity.findAll";
		public static final String findByName = "ServiceEntity.findByName";
		public static final String findByUnit = "ServiceEntity.findByUnit";
		public static final String findNotInThisUnit = "ServiceEntity.findNotInThisUnit";

	}

	List<ServiceEntity> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	List<ServiceEntity> findByName(String serviceName, EntityStatus entityStatus) throws BusinessException;

	List<ServiceEntity> findByUnit(Unit unit, EntityStatus entityStatus) throws BusinessException;

	List<ServiceEntity> findNotInThisUnit(Unit unit, EntityStatus entityStatus) throws BusinessException;
}
