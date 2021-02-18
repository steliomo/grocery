/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionDAO extends GenericDAO<ServiceDescription, Long> {

	class QUERY {
		public static final String findAll = "SELECT sd FROM ServiceDescription sd INNER JOIN FETCH sd.service s WHERE sd.entityStatus = :entityStatus ORDER BY s.name";

		public static final String fetchByUuid = "SELECT sd FROM ServiceDescription sd INNER JOIN FETCH sd.service s WHERE sd.uuid = :serviceDescriptionUuid";

		public static final String fetchByName = "SELECT sd FROM ServiceDescription sd INNER JOIN FETCH sd.service s WHERE CONCAT(s.name, ' ', sd.description) LIKE :serviceDescriptionName AND sd.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findAll = "ServiceDescription.findAll";

		public static final String fetchByUuid = "ServiceDescription.fetchByUuid";

		public static final String fetchByName = "ServiceDescription.fetchByName";
	}

	List<ServiceDescription> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	ServiceDescription fetchByUuid(String serviceDescriptionUuid) throws BusinessException;

	List<ServiceDescription> fetchByName(String serviceDescriptionName, EntityStatus entityStatus) throws BusinessException;
}
