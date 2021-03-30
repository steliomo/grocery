/**
 *
 */
package mz.co.grocery.core.saleable.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.saleable.dao.ServiceItemDAO;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceItemQueryServiceImpl.NAME)
public class ServiceItemQueryServiceImpl implements ServiceItemQueryService {

	public static final String NAME = "mz.co.grocery.core.saleable.service.ServiceItemQueryServiceImpl";

	@Inject
	private ServiceItemDAO serviceItemDAO;

	@Override
	public List<ServiceItem> fetchAllServiceItems(final int currentPage, final int maxResult) throws BusinessException {
		return this.serviceItemDAO.fetchAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countServiceItems() throws BusinessException {
		return this.serviceItemDAO.count();
	}

	@Override
	public ServiceItem fetchServiceItemByUuid(final String serviceItemUuid) throws BusinessException {
		return this.serviceItemDAO.fetchByUuid(serviceItemUuid);
	}

	@Override
	public List<ServiceItem> fetchServiceItemByName(final String serviceItemName) throws BusinessException {
		return this.serviceItemDAO.fetchByName(serviceItemName, EntityStatus.ACTIVE);
	}

	@Override
	public List<ServiceItem> fetchServiceItemsByServiceAndUnit(final mz.co.grocery.core.item.model.Service service, final Grocery unit)
			throws BusinessException {
		return this.serviceItemDAO.fetchByServiceAndUnit(service, unit, EntityStatus.ACTIVE);
	}

	@Override
	public List<ServiceItem> fetchServiceItemsNotInThisUnitByService(final mz.co.grocery.core.item.model.Service service, final Grocery unit)
			throws BusinessException {
		return this.serviceItemDAO.fetchNotInThisUnitByService(service, unit, EntityStatus.ACTIVE);
	}
}
