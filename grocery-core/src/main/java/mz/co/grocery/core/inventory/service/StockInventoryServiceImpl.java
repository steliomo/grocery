/**
 *
 */
package mz.co.grocery.core.inventory.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.inventory.dao.StockInventoryDAO;
import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(StockInventoryServiceImpl.NAME)
public class StockInventoryServiceImpl extends AbstractService implements StockInventoryService {

	public static final String NAME = "mz.co.grocery.core.inventory.service.StockInventoryServiceImpl";

	@Inject
	private StockInventoryDAO stockInventoryDAO;

	@Override
	public StockInventory createStockInventory(final UserContext userContext, final StockInventory stockInventory)
	        throws BusinessException {
		this.stockInventoryDAO.create(userContext, stockInventory);
		return stockInventory;
	}

	@Override
	public StockInventory updateStockInventory(final UserContext userContext, final StockInventory stockInventory)
	        throws BusinessException {
		this.stockInventoryDAO.update(userContext, stockInventory);
		return stockInventory;
	}
}
