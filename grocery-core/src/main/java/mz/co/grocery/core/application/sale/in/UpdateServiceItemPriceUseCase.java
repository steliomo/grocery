/**
 *
 */
package mz.co.grocery.core.application.sale.in;

import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface UpdateServiceItemPriceUseCase {

	ServiceItem updateServiceItemPrice(UserContext userContext, final ServiceItem serviceItem) throws BusinessException;
}
