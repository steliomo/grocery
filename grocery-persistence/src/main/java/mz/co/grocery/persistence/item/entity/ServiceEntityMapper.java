/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ServiceEntityMapper extends AbstractEntityMapper<ServiceEntity, Service> implements EntityMapper<ServiceEntity, Service> {

	@Override
	public ServiceEntity toEntity(final Service domain) {
		final ServiceEntity entity = new ServiceEntity();

		entity.setName(domain.getName());

		return this.toEntity(entity, domain);
	}

	@Override
	public Service toDomain(final ServiceEntity entity) {
		final Service service = new Service();

		service.setName(entity.getName());

		return this.toDomain(entity, service);
	}
}
