/**
 *
 */
package mz.co.grocery.core.saleable.service;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.saleable.model.Stock;
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

	Stock findStockByUuid(String stockUuid) throws BusinessException;

	List<Stock> fetchStockByGroceryAndProduct(String groceryUuid, String productUuid) throws BusinessException;

	List<Stock> fetchStocksByGrocery(String groceryUuid) throws BusinessException;

	List<Stock> fetchLowStocksByGroceryAndSalePeriod(String groceryUuid, LocalDate startDate, LocalDate endDate)
			throws BusinessException;

	List<Stock> fetchStockNotInthisGroceryByProduct(String groceryUuid, String productUuid) throws BusinessException;
}
