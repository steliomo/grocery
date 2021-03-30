/**
 *
 */
package mz.co.grocery.core.item.service;

import javax.inject.Inject;

import mz.co.grocery.core.item.dao.ServiceDAO;
import mz.co.grocery.core.item.model.Service;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@org.springframework.stereotype.Service(ServiceServiceImpl.NAME)
public class ServiceServiceImpl extends AbstractService implements ServiceService {

	public static final String NAME = "mz.co.grocery.core.item.service.ServiceServiceImpl";

	@Inject
	private ServiceDAO serviceDAO;

	@Override
	public Service createService(final UserContext userContext, final Service service) throws BusinessException {
		return this.serviceDAO.create(userContext, service);
	}

	@Override
	public Service updateService(final UserContext userContext, final Service service) throws BusinessException {
		return this.serviceDAO.update(userContext, service);
	}
}
