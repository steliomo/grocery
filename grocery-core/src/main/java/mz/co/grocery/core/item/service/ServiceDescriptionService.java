/**
 *
 */
package mz.co.grocery.core.item.service;

import mz.co.grocery.core.item.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionService {

	ServiceDescription createServiceDescription(UserContext userContext, ServiceDescription serviceDescription)
			throws BusinessException;

	ServiceDescription updateServiceDescription(UserContext userContext, ServiceDescription serviceDescription) throws BusinessException;

}
