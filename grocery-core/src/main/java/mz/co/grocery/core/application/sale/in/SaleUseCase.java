/**
 *
 */
package mz.co.grocery.core.application.sale.in;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleUseCase {

	Sale registSale(UserContext userContext, Sale sale) throws BusinessException;
}
