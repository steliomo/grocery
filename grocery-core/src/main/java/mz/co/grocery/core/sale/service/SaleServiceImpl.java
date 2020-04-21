/**
 *
 */
package mz.co.grocery.core.sale.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.dao.SaleItemDAO;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.stock.dao.StockDAO;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(SaleServiceImpl.NAME)
public class SaleServiceImpl extends AbstractService implements SaleService {

	public static final String NAME = "mz.co.grocery.core.sale.service.SaleServiceImpl";

	@Inject
	private SaleDAO saleDAO;

	@Inject
	private SaleItemDAO saleItemDAO;

	@Inject
	private StockDAO stockDAO;

	@Override
	public Sale registSale(final UserContext userContext, final Sale sale) throws BusinessException {

		if (sale.getItems().isEmpty()) {
			throw new BusinessException("cannot create a sale without items");
		}

		this.saleDAO.create(userContext, sale);

		for (final SaleItem saleItem : sale.getItems()) {
			final Stock stock = this.stockDAO.findByUuid(saleItem.getStock().getUuid());
			stock.updateStock(saleItem);
			this.stockDAO.update(userContext, stock);

			saleItem.setSale(sale);
			this.saleItemDAO.create(userContext, saleItem);
		}

		sale.calculateTotal();
		sale.calculateProfit();

		return sale;
	}

}
