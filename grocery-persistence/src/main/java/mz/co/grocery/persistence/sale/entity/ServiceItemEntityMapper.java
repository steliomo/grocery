/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.item.entity.ServiceDescriptionEntity;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ServiceItemEntityMapper extends AbstractEntityMapper<ServiceItemEntity, ServiceItem>
implements EntityMapper<ServiceItemEntity, ServiceItem> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<ServiceDescriptionEntity, ServiceDescription> serviceDescriptionMapper;

	public ServiceItemEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper,
			final EntityMapper<ServiceDescriptionEntity, ServiceDescription> serviceDescriptionMapper) {
		this.unitMapper = unitMapper;
		this.serviceDescriptionMapper = serviceDescriptionMapper;
	}

	@Override
	public ServiceItemEntity toEntity(final ServiceItem domain) {
		final ServiceItemEntity entity = new ServiceItemEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getServiceDescription()
		.ifPresent(serviceDescription -> entity.setServiceDescription(this.serviceDescriptionMapper.toEntity(serviceDescription)));
		entity.setSalePrice(domain.getSalePrice());

		return this.toEntity(entity, domain);
	}

	@Override
	public ServiceItem toDomain(final ServiceItemEntity entity) {
		final ServiceItem domain = new ServiceItem();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		entity.getServiceDescription()
		.ifPresent(serviceDescription -> domain.setServiceDescription(this.serviceDescriptionMapper.toDomain(serviceDescription)));
		domain.setSalePrice(entity.getSalePrice());

		return this.toDomain(entity, domain);
	}
}
