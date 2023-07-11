/**
 *
 */
package mz.co.grocery.persistence.sale.adapter;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.grocery.persistence.sale.repository.StockRepositoty;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class StockAdapter implements StockPort {

	private StockRepositoty repositoty;

	private EntityMapper<StockEntity, Stock> mapper;

	public StockAdapter(final StockRepositoty repositoty, final EntityMapper<StockEntity, Stock> mapper) {
		this.repositoty = repositoty;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Stock createStock(final UserContext userContext, final Stock stock) throws BusinessException {
		final StockEntity entity = this.mapper.toEntity(stock);

		this.repositoty.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Stock updateStock(final UserContext userContext, final Stock stock) throws BusinessException {
		final StockEntity entity = this.mapper.toEntity(stock);

		this.repositoty.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Stock removeStock(final UserContext userContext, final Stock stock) throws BusinessException {
		stock.inactivate();

		return this.updateStock(userContext, stock);
	}

	@Override
	public Stock regularize(final UserContext userContext, final Stock stock) throws BusinessException {

		final Stock stockToRegularize = this.mapper.toDomain(this.repositoty.findByUuid(stock.getUuid()));

		stockToRegularize.setQuantity(stock.getInventoryQuantity());
		stockToRegularize.setStockUpdateDate(LocalDate.now());
		stockToRegularize.setStockUpdateQuantity(stock.getInventoryQuantity());
		stockToRegularize.setStockStatus();
		stockToRegularize.setProductStockStatus();

		return this.updateStock(userContext, stockToRegularize);
	}

	@Override
	public List<Stock> fetchAllStocks(final int currentPage, final int maxResult) throws BusinessException {
		return this.repositoty.fetchAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long count(final EntityStatus entityStatus) throws BusinessException {
		return this.repositoty.count(entityStatus);
	}

	@Override
	public Stock fetchStockByUuid(final String stockUuid) throws BusinessException {
		return this.repositoty.fetchByUuid(stockUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStocksByProductDescription(final String description) throws BusinessException {
		return this.repositoty.fetchByProductDescription(description, EntityStatus.ACTIVE);
	}

	@Override
	public Stock findStockByUuid(final String stockUuid) throws BusinessException {
		return this.mapper.toDomain(this.repositoty.findByUuid(stockUuid));
	}

	@Override
	public List<Stock> fetchStockByGroceryAndProduct(final String groceryUuid, final String productUuid)
			throws BusinessException {
		return this.repositoty.fetchByGroceryAndProduct(groceryUuid, productUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStocksByGrocery(final String groceryUuid) throws BusinessException {
		return this.repositoty.fetchByGrocery(groceryUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchLowStocksByGroceryAndSalePeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate)
					throws BusinessException {
		return this.repositoty.fetchByGroceryAndSalePeriod(groceryUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStockNotInthisGroceryByProduct(final String groceryUuid, final String productUuid)
			throws BusinessException {
		return this.repositoty.fetchNotInThisGroceryByProduct(groceryUuid, productUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Stock> fetchStocksInAnalysisByUnit(final String unitUuid) throws BusinessException {
		return this.repositoty.fetchInAnalysisByUnitUuid(unitUuid, EntityStatus.ACTIVE);
	}
}
