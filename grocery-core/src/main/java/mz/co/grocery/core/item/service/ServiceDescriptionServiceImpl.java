/**
 *
 */
package mz.co.grocery.core.item.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.item.dao.ServiceDescriptionDAO;
import mz.co.grocery.core.item.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceDescriptionServiceImpl.NAME)
public class ServiceDescriptionServiceImpl extends AbstractService implements ServiceDescriptionService {

	public static final String NAME = "mz.co.grocery.core.item.service.ServiceDescriptionServiceImpl";

	@Inject
	private ServiceDescriptionDAO serviceDescriptionDAO;

	@Override
	public ServiceDescription createServiceDescription(final UserContext userContext, final ServiceDescription serviceDescription)
			throws BusinessException {
		return this.serviceDescriptionDAO.create(userContext, serviceDescription);
	}

	@Override
	public ServiceDescription updateServiceDescription(final UserContext userContext, final ServiceDescription serviceDescription)
			throws BusinessException {
		return this.serviceDescriptionDAO.update(userContext, serviceDescription);
	}
}
