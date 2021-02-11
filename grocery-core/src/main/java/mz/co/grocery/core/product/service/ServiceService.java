/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.Service;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceService {

	Service createService(UserContext userContext, Service service) throws BusinessException;

	Service updateService(UserContext userContext, Service service) throws BusinessException;
}
