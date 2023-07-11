/**
 *
 */
package mz.co.grocery.core.application.sale.in;

import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface UpdateStockAndPricesUseCase {
	Stock updateStocksAndPrices(final UserContext userContext, final Stock stock) throws BusinessException;
}
