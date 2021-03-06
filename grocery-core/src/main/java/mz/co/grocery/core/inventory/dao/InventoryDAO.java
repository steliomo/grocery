/**
 *
 */
package mz.co.grocery.core.inventory.dao;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface InventoryDAO extends GenericDAO<Inventory, Long> {

	class QUERY {
		public static final String fetchByGroceryAndStatus = "SELECT i FROM Inventory i INNER JOIN FETCH i.grocery LEFT JOIN FETCH i.stockInventories si LEFT JOIN FETCH si.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit WHERE i.grocery.uuid = :groceryUuid AND i.inventoryStatus = :inventoryStatus AND i.entityStatus = :entityStatus";
		public static final String fetchByUuid = "SELECT i FROM Inventory i LEFT JOIN FETCH i.stockInventories si LEFT JOIN FETCH si.stock s WHERE i.uuid = :inventoryUuid AND i.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String fetchByGroceryAndStatus = "Inventory.findByGroceryAndStatus";
		public static final String fetchByUuid = "Inventory.fetchByUuid";
	}

	Inventory fetchByGroceryAndStatus(Grocery grocery, InventoryStatus inventoryStatus, EntityStatus entityStatus)
	        throws BusinessException;

	Inventory fetchByUuid(String inventoryUuid, EntityStatus entityStatus) throws BusinessException;

}
