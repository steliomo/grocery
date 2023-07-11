/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.application.sale.in.UpdateStockAndPricesUseCase;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class UpdateStockAndPricesService extends AbstractService implements UpdateStockAndPricesUseCase {

	private StockPort stockPort;

	public UpdateStockAndPricesService(final StockPort stockPort) {
		this.stockPort = stockPort;
	}

	@Override
	public Stock updateStocksAndPrices(final UserContext userContext, final Stock stock) throws BusinessException {

		if (BigDecimal.ZERO.compareTo(stock.getPurchasePrice()) == BigDecimal.ZERO.intValue()
				|| BigDecimal.ZERO.compareTo(stock.getSalePrice()) == BigDecimal.ZERO.intValue()) {
			throw new BusinessException("the.prices.cannot.be.zero");
		}

		if (stock.getPurchasePrice().compareTo(stock.getSalePrice()) >= BigDecimal.ZERO.intValue()) {
			throw new BusinessException(
					"purchase.price.cannot.be.greater.than.or.equal.to.sale.price", new String[] { stock.getName() });
		}

		final Stock stockToUpdate = this.stockPort.findStockByUuid(stock.getUuid());

		stockToUpdate.setPurchasePrice(stock.getPurchasePrice());
		stockToUpdate.setSalePrice(stock.getSalePrice());
		stockToUpdate.setRentPrice(stock.getRentPrice());
		stockToUpdate.addQuantity(stock.getQuantity());
		stockToUpdate.setMinimumStock(stock.getMinimumStock());
		stockToUpdate.setUnitPerM2(stock.getUnitPerM2());

		stockToUpdate.setStockUpdateDate(LocalDate.now());
		stockToUpdate.setStockUpdateQuantity(stockToUpdate.getQuantity());

		this.stockPort.updateStock(userContext, stockToUpdate);

		return stockToUpdate;
	}
}
