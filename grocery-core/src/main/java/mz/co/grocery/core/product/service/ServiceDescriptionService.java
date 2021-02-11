/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionService {

	ServiceDescription createServiceDescription(UserContext userContext, ServiceDescription serviceDescription)
			throws BusinessException;

}
