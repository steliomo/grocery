/**
 *
 */
package mz.co.grocery.core.application.inventory.in;

import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ApproveInventoryUseCase {

	Inventory approveInventory(UserContext userContext, String inventoryUuid) throws BusinessException;

}
