/**
 *
 */
package mz.co.grocery.core.application.sale.out;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface StockPort {

	Stock createStock(UserContext userContext, Stock stock) throws BusinessException;

	Stock updateStock(UserContext userContext, Stock stock) throws BusinessException;

	Stock removeStock(UserContext userContext, Stock stock) throws BusinessException;

	Stock regularize(UserContext userContext, Stock stock) throws BusinessException;

	List<Stock> fetchAllStocks(int currentPage, int maxResult) throws BusinessException;

	Long count(EntityStatus entityStatus) throws BusinessException;

	Stock fetchStockByUuid(String stockUuid) throws BusinessException;

	List<Stock> fetchStocksByProductDescription(String description) throws BusinessException;

	Stock findStockByUuid(String stockUuid) throws BusinessException;

	Stock findStockById(Long stockId) throws BusinessException;

	List<Stock> fetchStockByGroceryAndProduct(String groceryUuid, String productUuid) throws BusinessException;

	List<Stock> fetchStocksByGrocery(String groceryUuid) throws BusinessException;

	List<Stock> fetchLowStocksByGroceryAndSalePeriod(String groceryUuid, LocalDate startDate, LocalDate endDate)
			throws BusinessException;

	List<Stock> fetchStockNotInthisGroceryByProduct(String groceryUuid, String productUuid) throws BusinessException;

	List<Stock> fetchStocksInAnalysisByUnit(String unitUuid) throws BusinessException;

}
