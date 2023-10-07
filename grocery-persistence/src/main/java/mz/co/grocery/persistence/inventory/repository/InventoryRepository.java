/**
 *
 */
package mz.co.grocery.persistence.inventory.repository;

import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.inventory.entity.InventoryEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface InventoryRepository extends GenericDAO<InventoryEntity, Long> {

	class QUERY {
		public static final String fetchByGroceryAndStatus = "SELECT i FROM InventoryEntity i INNER JOIN FETCH i.unit LEFT JOIN FETCH i.stockInventories si LEFT JOIN FETCH si.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit WHERE i.unit.uuid = :groceryUuid AND i.inventoryStatus = :inventoryStatus AND i.entityStatus = :entityStatus";
		public static final String fetchByUuid = "SELECT i FROM InventoryEntity i LEFT JOIN FETCH i.stockInventories si LEFT JOIN FETCH si.stock s WHERE i.uuid = :inventoryUuid AND i.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String fetchByGroceryAndStatus = "InventoryEntity.findByGroceryAndStatus";
		public static final String fetchByUuid = "InventoryEntity.fetchByUuid";
	}

	InventoryEntity fetchByGroceryAndStatus(Unit grocery, InventoryStatus inventoryStatus, EntityStatus entityStatus)
			throws BusinessException;

	InventoryEntity fetchByUuid(String inventoryUuid, EntityStatus entityStatus) throws BusinessException;

}
