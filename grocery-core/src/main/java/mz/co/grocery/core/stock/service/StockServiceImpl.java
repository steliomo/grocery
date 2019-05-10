/**
 *
 */
package mz.co.grocery.core.stock.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.stock.dao.StockDAO;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(StockServiceImpl.NAME)
public class StockServiceImpl extends AbstractService implements StockService {

	public static final String NAME = "mz.co.grocery.core.stock.service.StockServiceImpl";

	@Inject
	private StockDAO stockDAO;

	@Override
	public Stock createStock(final UserContext userContext, final Stock stock) throws BusinessException {
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

}
