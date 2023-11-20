/**
 *
 */
package mz.co.grocery.core.application.pos.out;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleListner {

	Sale send(UserContext context, Sale sale) throws BusinessException;
}
