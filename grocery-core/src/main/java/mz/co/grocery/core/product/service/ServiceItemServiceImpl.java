/**
 *
 */
package mz.co.grocery.core.product.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ServiceItemDAO;
import mz.co.grocery.core.product.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceItemServiceImpl.NAME)
public class ServiceItemServiceImpl extends AbstractService implements ServiceItemService {

	public static final String NAME = "mz.co.grocery.core.product.service.ServiceItemServiceImpl";

	@Inject
	private ServiceItemDAO serviceItemDAO;

	@Override
	public ServiceItem createServiceItem(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {
		return this.serviceItemDAO.create(userContext, serviceItem);
	}
}
