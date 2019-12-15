/**
 *
 */
package mz.co.grocery.core.inventory.service;

import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface StockInventoryService {

	StockInventory createStockInventory(UserContext userContext, StockInventory stockInventory)
	        throws BusinessException;

	StockInventory updateStockInventory(UserContext userContext, StockInventory stockInventory)
	        throws BusinessException;

}
