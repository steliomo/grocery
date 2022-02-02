/**
 *
 */
package mz.co.grocery.core.saleable.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */

@Service(StockQueryServiceImpl.NAME)
public class StockQueryServiceImpl implements StockQueryService {

	public static final String NAME = "mz.co.grocery.core.saleable.service.StockQueryServiceImpl";

	@Inject
	private StockDAO stockDAO;

	@Override
	public List<Stock> fetchAllStocks(final int currentPage, final int maxResult) throws BusinessException {
		return this.stockDAO.fetchAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long count(final EntityStatus entityStatus) throws BusinessException {
		return this.stockDAO.count(entityStatus);
	}

	@Override
	public Stock fetchStockByUuid(final String stockUuid) throws BusinessException {
		return this.stockDAO.fetchByUuid(stockUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStocksByProductDescription(final String description) throws BusinessException {
		return this.stockDAO.fetchByProductDescription(description, EntityStatus.ACTIVE);
	}

	@Override
	public Stock findStockByUuid(final String stockUuid) throws BusinessException {
		return this.stockDAO.findByUuid(stockUuid);
	}

	@Override
	public List<Stock> fetchStockByGroceryAndProduct(final String groceryUuid, final String productUuid)
			throws BusinessException {
		return this.stockDAO.fetchByGroceryAndProduct(groceryUuid, productUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStocksByGrocery(final String groceryUuid) throws BusinessException {
		return this.stockDAO.fetchByGrocery(groceryUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchLowStocksByGroceryAndSalePeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate)
					throws BusinessException {
		return this.stockDAO.fetchByGroceryAndSalePeriod(groceryUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStockNotInthisGroceryByProduct(final String groceryUuid, final String productUuid)
			throws BusinessException {
		return this.stockDAO.fetchNotInThisGroceryByProduct(groceryUuid, productUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStocksInAnalysisByUnit(final String unitUuid) throws BusinessException {
		return this.stockDAO.fetchInAnalysisByUnitUuid(unitUuid, EntityStatus.ACTIVE);
	}
}
