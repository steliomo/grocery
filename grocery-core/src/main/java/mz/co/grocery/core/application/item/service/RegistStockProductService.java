/**
 *
 */
package mz.co.grocery.core.application.item.service;

import java.util.Optional;

import mz.co.grocery.core.application.item.in.RegistStockProductUseCase;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class RegistStockProductService implements RegistStockProductUseCase {

	private StockPort stockPort;

	public RegistStockProductService(final StockPort stockPort) {
		this.stockPort = stockPort;
	}

	@Override
	public Stock regist(final UserContext userContext, Stock stock) throws BusinessException {

		final Optional<Stock> optionalStock = this.stockPort.fetchStockByProductAndUnit(stock.getProductDescription().get(), stock.getUnit().get());

		if (optionalStock.isPresent()) {
			throw new BusinessException("stock.product.already.exist", stock.getProductDescription().get().getName(),
					stock.getUnit().get().getName());
		}

		stock = this.stockPort.createStock(userContext, stock);

		return stock;
	}
}

