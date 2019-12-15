/**
 *
 */
package mz.co.grocery.core.inventory.service;

import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface InventoryService {

	Inventory createInventory(UserContext userContext, Inventory inventory) throws BusinessException;

	Inventory performInventory(UserContext userContext, Inventory inventory) throws BusinessException;

	Inventory approveInventory(UserContext userContext, Inventory inventory) throws BusinessException;

	Inventory updateInventory(UserContext userContext, Inventory inventory) throws BusinessException;
}
