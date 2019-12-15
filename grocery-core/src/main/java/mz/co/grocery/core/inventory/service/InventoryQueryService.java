/**
 *
 */
package mz.co.grocery.core.inventory.service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface InventoryQueryService {

	Inventory fetchInventoryByGroceryAndStatus(Grocery grocery, InventoryStatus inventoryStatus)
	        throws BusinessException;

}
