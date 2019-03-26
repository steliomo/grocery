/**
 *
 */
package mz.co.grocery.core.stock.service;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface StockService {

	Stock createStock(UserContext userContext, Stock stock) throws BusinessException;

}
