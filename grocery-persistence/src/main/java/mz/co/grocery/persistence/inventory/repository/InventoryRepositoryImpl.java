/**
 *
 */
package mz.co.grocery.persistence.inventory.repository;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.inventory.entity.InventoryEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class InventoryRepositoryImpl extends GenericDAOImpl<InventoryEntity, Long> implements InventoryRepository {

	@Override
	public InventoryEntity fetchByGroceryAndStatus(final Unit grocery, final InventoryStatus inventoryStatus,
			final EntityStatus entityStatus) throws BusinessException {
		try {
			return this.findSingleByNamedQuery(InventoryRepository.QUERY_NAME.fetchByGroceryAndStatus,
					new ParamBuilder().add("groceryUuid", grocery.getUuid()).add("inventoryStatus", inventoryStatus)
					.add("entityStatus", entityStatus).process());
		} catch (final NoResultException exception) {
			throw new BusinessException("Inventory not found");
		}
	}

	@Override
	public InventoryEntity fetchByUuid(final String inventoryUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(InventoryRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("inventoryUuid", inventoryUuid).add("entityStatus", entityStatus).process());
	}
}
