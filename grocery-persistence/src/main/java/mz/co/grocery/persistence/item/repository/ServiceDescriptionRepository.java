/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.persistence.item.entity.ServiceDescriptionEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionRepository extends GenericDAO<ServiceDescriptionEntity, Long> {

	class QUERY {
		public static final String findAll = "SELECT sd FROM ServiceDescriptionEntity sd INNER JOIN FETCH sd.service s WHERE sd.entityStatus = :entityStatus ORDER BY s.name";

		public static final String fetchByUuid = "SELECT sd FROM ServiceDescriptionEntity sd INNER JOIN FETCH sd.service s WHERE sd.uuid = :serviceDescriptionUuid";

		public static final String fetchByName = "SELECT sd FROM ServiceDescriptionEntity sd INNER JOIN FETCH sd.service s WHERE CONCAT(s.name, ' ', sd.description) LIKE :serviceDescriptionName AND sd.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findAll = "ServiceDescriptionEntity.findAll";

		public static final String fetchByUuid = "ServiceDescriptionEntity.fetchByUuid";

		public static final String fetchByName = "ServiceDescriptionEntity.fetchByName";
	}

	List<ServiceDescription> findAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	ServiceDescription fetchByUuid(String serviceDescriptionUuid) throws BusinessException;

	List<ServiceDescription> fetchByName(String serviceDescriptionName, EntityStatus entityStatus) throws BusinessException;
}
