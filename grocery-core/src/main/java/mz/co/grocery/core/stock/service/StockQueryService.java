/**
 *
 */
package mz.co.grocery.core.stock.service;

import java.util.List;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface StockQueryService {

	List<Stock> fetchAllStocks(int currentPage, int maxResult) throws BusinessException;

	Long count(EntityStatus entityStatus) throws BusinessException;

	Stock fetchStockByUuid(String stockUuid) throws BusinessException;

	List<Stock> fetchStocksByProductDescription(String description) throws BusinessException;
}
