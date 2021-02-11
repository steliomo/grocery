/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceItemService {

	ServiceItem createServiceItem(UserContext userContext, ServiceItem serviceItem) throws BusinessException;
}
