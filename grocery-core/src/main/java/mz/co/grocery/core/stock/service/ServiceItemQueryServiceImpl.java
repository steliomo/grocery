/**
 *
 */
package mz.co.grocery.core.stock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.stock.dao.ServiceItemDAO;
import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceItemQueryServiceImpl.NAME)
public class ServiceItemQueryServiceImpl implements ServiceItemQueryService {

	public static final String NAME = "mz.co.grocery.core.stock.service.ServiceItemQueryServiceImpl";

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
}
