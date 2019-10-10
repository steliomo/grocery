/**
 *
 */
package mz.co.grocery.core.inventory.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(InventoryQueryServiceImpl.NAME)
public class InventoryQueryServiceImpl implements InventoryQueryService {

	public static final String NAME = "mz.co.grocery.core.inventory.service.InventoryQueryServiceImpl";

	@Inject
	private InventoryDAO inventoryDAO;

	@Override
	public Inventory fetchInventoryByGroceryAndStatus(final Grocery grocery, final InventoryStatus inventoryStatus)
	        throws BusinessException {
		return this.inventoryDAO.fetchByGroceryAndStatus(grocery, inventoryStatus, EntityStatus.ACTIVE);
	}

}
