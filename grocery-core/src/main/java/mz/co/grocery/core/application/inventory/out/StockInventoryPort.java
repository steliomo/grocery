/**
 *
 */
package mz.co.grocery.core.application.inventory.out;

import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface StockInventoryPort {

	StockInventory createStockInventory(UserContext userContext, StockInventory stockInventory)
	        throws BusinessException;

	StockInventory updateStockInventory(UserContext userContext, StockInventory stockInventory)
	        throws BusinessException;

}
