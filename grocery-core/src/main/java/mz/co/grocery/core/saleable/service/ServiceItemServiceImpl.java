/**
 *
 */
package mz.co.grocery.core.saleable.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.saleable.dao.ServiceItemDAO;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceItemServiceImpl.NAME)
public class ServiceItemServiceImpl extends AbstractService implements ServiceItemService {

	public static final String NAME = "mz.co.grocery.core.saleable.service.ServiceItemServiceImpl";

	@Inject
	private ServiceItemDAO serviceItemDAO;

	@Inject
	private ApplicationTranslator translator;

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
			throw new BusinessException(this.translator.getTranslation("the.service.item.price.cannot.be.zero"));
		}

		final ServiceItem foundServiceItem = this.serviceItemDAO.findById(serviceItem.getId());
		foundServiceItem.setSalePrice(serviceItem.getSalePrice());

		this.updateServiceItem(userContext, foundServiceItem);

		return foundServiceItem;
	}
}
