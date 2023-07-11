/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
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
public class ServiceItemRepositoryImpl extends GenericDAOImpl<ServiceItemEntity, Long> implements ServiceItemRepository {

	private EntityMapper<ServiceItemEntity, ServiceItem> mapper;

	public ServiceItemRepositoryImpl(final EntityMapper<ServiceItemEntity, ServiceItem> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<ServiceItem> fetchAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {
		final List<Long> serviceItemIds = this
				.findByQuery(ServiceItemRepository.QUERY_NAME.findAllIds, new ParamBuilder().add("entityStatus", entityStatus).process(),
						Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (serviceItemIds.isEmpty()) {
			return new ArrayList<ServiceItem>();
		}

		return this
				.findByNamedQuery(ServiceItemRepository.QUERY_NAME.fetchAll, new ParamBuilder().add("serviceItemIds", serviceItemIds).process())
				.stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public ServiceItem fetchByUuid(final String serviceItemUuid) throws BusinessException {
		return this.mapper.toDomain(this.findSingleByNamedQuery(ServiceItemRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("serviceItemUuid", serviceItemUuid).process()));
	}

	@Override
	public List<ServiceItem> fetchByName(final String serviceItemName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceItemRepository.QUERY_NAME.fetchByName,
				new ParamBuilder().add("serviceItemName", "%" + serviceItemName + "%").add("entityStatus", entityStatus).process()).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<ServiceItem> fetchByServiceAndUnit(final Service service, final Unit unit, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ServiceItemRepository.QUERY_NAME.fetchByServiceAndUnit,
				new ParamBuilder().add("serviceUuid", service.getUuid()).add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process())
				.stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<ServiceItem> fetchNotInThisUnitByService(final Service service, final Unit unit, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ServiceItemRepository.QUERY_NAME.fetchNotInThisUnitByService,
				new ParamBuilder().add("serviceUuid", service.getUuid()).add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process())
				.stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}
}
