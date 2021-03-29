/**
 *
 */
package mz.co.grocery.core.stock.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.stock.dao.ServiceItemDAO;
import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceItemServiceImpl.NAME)
public class ServiceItemServiceImpl extends AbstractService implements ServiceItemService {

	public static final String NAME = "mz.co.grocery.core.stock.service.ServiceItemServiceImpl";

	@Inject
	private ServiceItemDAO serviceItemDAO;

	@Override
	public ServiceItem createServiceItem(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {
		return this.serviceItemDAO.create(userContext, serviceItem);
	}

	@Override
	public ServiceItem updateServiceItem(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {
		return this.serviceItemDAO.update(userContext, serviceItem);
	}

	@Override
	public ServiceItem updateServiceItemPrice(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {

		if (BigDecimal.ZERO.doubleValue() == serviceItem.getSalePrice().doubleValue()) {
			throw new BusinessException("The service item price cannot be O !");
		}

		final ServiceItem foundServiceItem = this.serviceItemDAO.findById(serviceItem.getId());
		foundServiceItem.setSalePrice(serviceItem.getSalePrice());

		this.updateServiceItem(userContext, foundServiceItem);

		return foundServiceItem;
	}
}
