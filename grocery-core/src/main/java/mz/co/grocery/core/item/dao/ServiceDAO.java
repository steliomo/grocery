/**
 *
 */
package mz.co.grocery.core.item.dao;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.Service;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDAO extends GenericDAO<Service, Long> {

	class QUERY {
		public static final String findAll = "SELECT s FROM Service s WHERE s.entityStatus = :entityStatus ORDER BY s.name";
		public static final String findByName = "SELECT s FROM Service s WHERE s.entityStatus = :entityStatus AND s.name LIKE :serviceName ORDER BY s.name";
		public static final String findByUnit = "SELECT s FROM ServiceItem si INNER JOIN si.serviceDescription sd INNER JOIN sd.service s INNER JOIN si.unit u WHERE u.uuid = :unitUuid AND si.entityStatus = :entityStatus GROUP BY s.id ORDER BY s.name";
		public static final String findNotInThisUnit = "SELECT s FROM ServiceItem si INNER JOIN si.serviceDescription sd INNER JOIN sd.service s WHERE NOT EXISTS "
				+ "( SELECT sde FROM ServiceItem sit INNER JOIN sit.serviceDescription sde INNER JOIN sit.unit u WHERE u.uuid = :unitUuid AND sde.id = sd.id) AND s.entityStatus = :entityStatus GROUP BY s.id ORDER BY s.name";
	}

	class QUERY_NAME {

		public static final String findAll = "Service.findAll";
		public static final String findByName = "Service.findByName";
		public static final String findByUnit = "Service.findByUnit";
		public static final String findNotInThisUnit = "Service.findNotInThisUnit";

	}

	List<Service> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	List<Service> findByName(String serviceName, EntityStatus entityStatus) throws BusinessException;

	List<Service> findByUnit(Grocery unit, EntityStatus entityStatus) throws BusinessException;

	List<Service> findNotInThisUnit(Grocery unit, EntityStatus entityStatus) throws BusinessException;
}
