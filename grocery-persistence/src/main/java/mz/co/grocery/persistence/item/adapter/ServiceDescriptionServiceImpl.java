/**
 *
 */
package mz.co.grocery.persistence.item.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.item.out.ServiceDescriptionPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.persistence.item.entity.ServiceDescriptionEntity;
import mz.co.grocery.persistence.item.repository.ServiceDescriptionRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class ServiceDescriptionServiceImpl implements ServiceDescriptionPort {

	private ServiceDescriptionRepository repository;

	private EntityMapper<ServiceDescriptionEntity, ServiceDescription> mapper;

	public ServiceDescriptionServiceImpl(final ServiceDescriptionRepository repository,
			final EntityMapper<ServiceDescriptionEntity, ServiceDescription> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public ServiceDescription createServiceDescription(final UserContext userContext, final ServiceDescription serviceDescription)
			throws BusinessException {
		final ServiceDescriptionEntity entity = this.mapper.toEntity(serviceDescription);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public ServiceDescription updateServiceDescription(final UserContext userContext, final ServiceDescription serviceDescription)
			throws BusinessException {
		final ServiceDescriptionEntity entity = this.mapper.toEntity(serviceDescription);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<ServiceDescription> findAllServiceDescriptions(final int currentPage, final int maxResult) throws BusinessException {
		return this.repository.findAll(currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countServiceDescriptions() throws BusinessException {
		return this.repository.count();
	}

	@Override
	public ServiceDescription fetchServiceDescriptionByUuid(final String serviceDescriptionUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.fetchByUuid(serviceDescriptionUuid));
	}

	@Override
	public List<ServiceDescription> fetchServiceDescriptionByName(final String serviceDescriptionName) throws BusinessException {
		return this.repository.fetchByName(serviceDescriptionName, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}
}
