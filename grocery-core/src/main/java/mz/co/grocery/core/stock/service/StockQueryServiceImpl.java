/**
 *
 */
package mz.co.grocery.core.stock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.stock.dao.StockDAO;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */

@Service(StockQueryServiceImpl.NAME)
public class StockQueryServiceImpl implements StockQueryService {

	public static final String NAME = "mz.co.grocery.core.stock.service.StockQueryServiceImpl";

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

}
