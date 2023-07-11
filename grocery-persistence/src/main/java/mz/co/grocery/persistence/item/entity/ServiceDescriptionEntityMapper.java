/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ServiceDescriptionEntityMapper extends AbstractEntityMapper<ServiceDescriptionEntity, ServiceDescription>
implements EntityMapper<ServiceDescriptionEntity, ServiceDescription> {

	private EntityMapper<ServiceEntity, Service> serviceMapper;

	public ServiceDescriptionEntityMapper(final EntityMapper<ServiceEntity, Service> serviceMapper) {
		this.serviceMapper = serviceMapper;
	}

	@Override
	public ServiceDescriptionEntity toEntity(final ServiceDescription domain) {
		final ServiceDescriptionEntity entity = new ServiceDescriptionEntity();

		domain.getService().ifPresent(service -> entity.setService(this.serviceMapper.toEntity(service)));
		entity.setDescription(domain.getDescription());

		return this.toEntity(entity, domain);
	}

	@Override
	public ServiceDescription toDomain(final ServiceDescriptionEntity entity) {
		final ServiceDescription domain = new ServiceDescription();

		entity.getService().ifPresent(service -> domain.setService(this.serviceMapper.toDomain(service)));
		domain.setDescription(entity.getDescription());

		return this.toDomain(entity, domain);
	}
}
