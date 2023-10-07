/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.item.entity.ServiceDescriptionEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class ServiceDescriptionRepositoryImpl extends GenericDAOImpl<ServiceDescriptionEntity, Long>
implements ServiceDescriptionRepository {

	@Override
	public List<ServiceDescriptionEntity> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(ServiceDescriptionRepository.QUERY_NAME.findAll, new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();
	}

	@Override
	public ServiceDescriptionEntity fetchByUuid(final String serviceDescriptionUuid) throws BusinessException {
		return this.findSingleByNamedQuery(ServiceDescriptionRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("serviceDescriptionUuid", serviceDescriptionUuid).process());
	}

	@Override
	public List<ServiceDescriptionEntity> fetchByName(final String serviceDescriptionName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceDescriptionRepository.QUERY_NAME.fetchByName,
				new ParamBuilder().add("serviceDescriptionName", "%" + serviceDescriptionName + "%").add("entityStatus", entityStatus).process());
	}
}
