/**
 *
 */
package mz.co.grocery.core.application.item.in;

import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RegistStockProductUseCase {

	Stock regist(UserContext userContext, Stock stock) throws BusinessException;
}
