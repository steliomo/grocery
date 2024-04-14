/**
 *
 */
package mz.co.grocery.persistence.sale.adapter;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
import mz.co.grocery.persistence.sale.repository.ServiceItemRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class ServiceItemAdapter implements ServiceItemPort {

	private ServiceItemRepository repository;

	private EntityMapper<ServiceItemEntity, ServiceItem> mapper;

	public ServiceItemAdapter(final ServiceItemRepository repository, final EntityMapper<ServiceItemEntity, ServiceItem> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public ServiceItem createServiceItem(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {
		final ServiceItemEntity entity = this.mapper.toEntity(serviceItem);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public ServiceItem updateServiceItem(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {
		final ServiceItemEntity entity = this.mapper.toEntity(serviceItem);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<ServiceItem> fetchAllServiceItems(final int currentPage, final int maxResult) throws BusinessException {
		return this.repository.fetchAll(currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public Long countServiceItems() throws BusinessException {
		return this.repository.count();
	}

	@Override
	public ServiceItem fetchServiceItemByUuid(final String serviceItemUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.fetchByUuid(serviceItemUuid));
	}

	@Override
	public List<ServiceItem> fetchServiceItemByName(final String serviceItemName) throws BusinessException {
		return this.repository.fetchByName(serviceItemName, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<ServiceItem> fetchServiceItemsByServiceAndUnit(final mz.co.grocery.core.domain.item.Service service, final Unit unit)
			throws BusinessException {
		return this.repository.fetchByServiceAndUnit(service, unit, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<ServiceItem> fetchServiceItemsNotInThisUnitByService(final mz.co.grocery.core.domain.item.Service service, final Unit unit)
			throws BusinessException {
		return this.repository.fetchNotInThisUnitByService(service, unit, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public ServiceItem findsaleItemById(final Long id) throws BusinessException {
		return this.mapper.toDomain(this.repository.findById(id));
	}
}
