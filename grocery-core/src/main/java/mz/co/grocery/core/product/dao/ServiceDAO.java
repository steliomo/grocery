/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import mz.co.grocery.core.product.model.Service;
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
		public static final String findByName = "SELECT s FROM Service s WHERE s.entityStatus = :entityStatus AND s.name LIKE CONCAT('%', :serviceName, '%') ORDER BY s.name";
	}

	class QUERY_NAME {

		public static final String findAll = "Service.findAll";
		public static final String findByName = "Service.findByName";

	}

	List<Service> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	List<Service> findByName(String serviceName, EntityStatus entityStatus) throws BusinessException;
}
