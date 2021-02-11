/**
 *
 */
package mz.co.grocery.core.inventory.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(InventoryDAOImpl.NAME)
public class InventoryDAOImpl extends GenericDAOImpl<Inventory, Long> implements InventoryDAO {

	public static final String NAME = "mz.co.grocery.core.inventory.dao.InventoryDAOImpl";

	@Override
	public Inventory fetchByGroceryAndStatus(final Grocery grocery, final InventoryStatus inventoryStatus,
			final EntityStatus entityStatus) throws BusinessException {
		try {

			return this.findSingleByNamedQuery(InventoryDAO.QUERY_NAME.fetchByGroceryAndStatus,
					new ParamBuilder().add("groceryUuid", grocery.getUuid()).add("inventoryStatus", inventoryStatus)
					.add("entityStatus", entityStatus).process());
		} catch (final NoResultException exception) {
			throw new BusinessException("Inventory not found");
		}
	}

	@Override
	public Inventory fetchByUuid(final String inventoryUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(InventoryDAO.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("inventoryUuid", inventoryUuid).add("entityStatus", entityStatus).process());
	}

}
