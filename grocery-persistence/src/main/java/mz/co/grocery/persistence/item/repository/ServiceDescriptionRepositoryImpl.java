/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.persistence.item.entity.ServiceDescriptionEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class ServiceDescriptionRepositoryImpl extends GenericDAOImpl<ServiceDescriptionEntity, Long>
implements ServiceDescriptionRepository {

	private EntityMapper<ServiceDescriptionEntity, ServiceDescription> mapper;

	public ServiceDescriptionRepositoryImpl(final EntityMapper<ServiceDescriptionEntity, ServiceDescription> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<ServiceDescription> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(ServiceDescriptionRepository.QUERY_NAME.findAll, new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList().stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public ServiceDescription fetchByUuid(final String serviceDescriptionUuid) throws BusinessException {
		return this.mapper.toDomain(this.findSingleByNamedQuery(ServiceDescriptionRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("serviceDescriptionUuid", serviceDescriptionUuid).process()));
	}

	@Override
	public List<ServiceDescription> fetchByName(final String serviceDescriptionName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceDescriptionRepository.QUERY_NAME.fetchByName,
				new ParamBuilder().add("serviceDescriptionName", "%" + serviceDescriptionName + "%").add("entityStatus", entityStatus).process())
				.stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}
}
