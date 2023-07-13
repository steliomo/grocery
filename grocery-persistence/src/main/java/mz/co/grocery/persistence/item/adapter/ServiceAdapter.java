/**
 *
 */
package mz.co.grocery.persistence.item.adapter;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.item.out.ServicePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ServiceEntity;
import mz.co.grocery.persistence.item.repository.ServiceRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class ServiceAdapter extends AbstractService implements ServicePort {

	private ServiceRepository repository;

	private EntityMapper<ServiceEntity, Service> mapper;

	public ServiceAdapter(final ServiceRepository repository, final EntityMapper<ServiceEntity, Service> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Service createService(final UserContext userContext, final Service service) throws BusinessException {
		final ServiceEntity entity = this.mapper.toEntity(service);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Service updateService(final UserContext userContext, final Service service) throws BusinessException {
		final ServiceEntity entity = this.mapper.toEntity(service);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Long countServices() throws BusinessException {
		return this.repository.count();
	}

	@Override
	public List<Service> findAllServices(final int currentPage, final int maxResult) throws BusinessException {
		return this.repository.findAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Service findServiceByUuid(final String serviceUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(serviceUuid));
	}

	@Override
	public List<Service> findServicesByName(final String serviceName) throws BusinessException {
		return this.repository.findByName(serviceName, EntityStatus.ACTIVE);
	}

	@Override
	public List<Service> findServicesByUnit(final Unit unit) throws BusinessException {
		return this.repository.findByUnit(unit, EntityStatus.ACTIVE);
	}

	@Override
	public List<Service> findServicesNotInthisUnit(final Unit unit) throws BusinessException {
		return this.repository.findNotInThisUnit(unit, EntityStatus.ACTIVE);
	}
}
