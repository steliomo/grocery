/**
 *
 */
package mz.co.grocery.core.stock.service;

import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceItemService {

	ServiceItem createServiceItem(UserContext userContext, ServiceItem serviceItem) throws BusinessException;

	ServiceItem updateServiceItem(UserContext userContext, ServiceItem serviceItem) throws BusinessException;
}
