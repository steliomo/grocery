/**
 *
 */
package mz.co.grocery.core.application.inventory.out;

import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface InventoryPort {

	Inventory createInventory(UserContext userContext, Inventory inventory) throws BusinessException;

	Inventory updateInventory(UserContext userContext, Inventory inventory) throws BusinessException;

	Inventory fetchInventoryByGroceryAndStatus(Unit grocery, InventoryStatus inventoryStatus)
			throws BusinessException;

	Inventory fetchInventoryUuid(String inventoryUuid) throws BusinessException;
}
