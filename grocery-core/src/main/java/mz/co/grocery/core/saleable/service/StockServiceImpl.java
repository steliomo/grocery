/**
 *
 */
package mz.co.grocery.core.saleable.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(StockServiceImpl.NAME)
public class StockServiceImpl extends AbstractService implements StockService {

	public static final String NAME = "mz.co.grocery.core.saleable.service.StockServiceImpl";

	@Inject
	private StockDAO stockDAO;

	@Inject
	private ApplicationTranslator translator;

	@Override
	public Stock createStock(final UserContext userContext, final Stock stock) throws BusinessException {
		stock.setStockStatus();
		this.stockDAO.create(userContext, stock);
		return stock;
	}

	@Override
	public Stock updateStock(final UserContext userContext, final Stock stock) throws BusinessException {
		this.stockDAO.update(userContext, stock);
		return stock;
	}

	@Override
	public Stock removeStock(final UserContext userContext, final Stock stock) throws BusinessException {
		stock.inactive();
		this.stockDAO.update(userContext, stock);
		return stock;
	}

	@Override
	public Stock updateStocksAndPrices(final UserContext userContext, final Stock stock) throws BusinessException {

		if (BigDecimal.ZERO.compareTo(stock.getPurchasePrice()) == BigDecimal.ZERO.intValue()
				|| BigDecimal.ZERO.compareTo(stock.getSalePrice()) == BigDecimal.ZERO.intValue()) {
			throw new BusinessException(this.translator.getTranslation("the.prices.cannot.be.zero"));
		}

		if (stock.getPurchasePrice().compareTo(stock.getSalePrice()) >= BigDecimal.ZERO.intValue()) {
			throw new BusinessException(
					this.translator.getTranslation("purchase.price.cannot.be.greater.than.or.equal.to.sale.price", new String[] { stock.getName() }));
		}

		final Stock stockToUpdate = this.stockDAO.findByUuid(stock.getUuid());

		stockToUpdate.setPurchasePrice(stock.getPurchasePrice());
		stockToUpdate.setSalePrice(stock.getSalePrice());
		stockToUpdate.setRentPrice(stock.getRentPrice());
		stockToUpdate.addQuantity(stock.getQuantity());
		stockToUpdate.setMinimumStock(stock.getMinimumStock());
		stockToUpdate.setUnitPerM2(stock.getUnitPerM2());

		stockToUpdate.setStockUpdateDate(LocalDate.now());
		stockToUpdate.setStockUpdateQuantity(stockToUpdate.getQuantity());

		this.updateStock(userContext, stockToUpdate);

		return stockToUpdate;
	}

	@Override
	public Stock regularize(final UserContext userContext, final Stock stock) throws BusinessException {

		final Stock stockToRegularize = this.stockDAO.findByUuid(stock.getUuid());

		stockToRegularize.setQuantity(stock.getInventoryQuantity());
		stockToRegularize.setStockUpdateDate(LocalDate.now());
		stockToRegularize.setStockUpdateQuantity(stock.getInventoryQuantity());
		stockToRegularize.setStockStatus();
		stockToRegularize.setProductStockStatus();

		return stockToRegularize;
	}
}
