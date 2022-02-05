/**
 *
 */
package mz.co.grocery.core.saleable.service;

import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface StockService {

	Stock createStock(UserContext userContext, Stock stock) throws BusinessException;

	Stock updateStock(UserContext userContext, Stock stock) throws BusinessException;

	Stock removeStock(UserContext userContext, Stock stock) throws BusinessException;

	Stock updateStocksAndPrices(UserContext userContext, Stock stock) throws BusinessException;

	Stock regularize(UserContext userContext, Stock stock) throws BusinessException;

}
