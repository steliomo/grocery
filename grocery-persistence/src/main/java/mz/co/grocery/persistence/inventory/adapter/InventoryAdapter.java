/**
 *
 */
package mz.co.grocery.persistence.inventory.adapter;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.inventory.entity.InventoryEntity;
import mz.co.grocery.persistence.inventory.repository.InventoryRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class InventoryAdapter implements InventoryPort {

	private InventoryRepository repository;

	private EntityMapper<InventoryEntity, Inventory> mapper;

	public InventoryAdapter(final InventoryRepository inventoryRepository,
			final EntityMapper<InventoryEntity, Inventory> mapper) {
		this.repository = inventoryRepository;
		this.mapper = mapper;
	}

	@Override
	public Inventory fetchInventoryByGroceryAndStatus(final Unit grocery, final InventoryStatus inventoryStatus)
			throws BusinessException {
		return this.repository.fetchByGroceryAndStatus(grocery, inventoryStatus, EntityStatus.ACTIVE);
	}

	@Override
	public Inventory fetchInventoryUuid(final String inventoryUuid) throws BusinessException {
		return this.repository.fetchByUuid(inventoryUuid, EntityStatus.ACTIVE);
	}

	@Transactional
	@Override
	public Inventory createInventory(final UserContext userContext, final Inventory inventory) throws BusinessException {

		final InventoryEntity entity = this.mapper.toEntity(inventory);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Inventory updateInventory(final UserContext userContext, final Inventory inventory) throws BusinessException {

		final InventoryEntity entity = this.mapper.toEntity(inventory);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}
}
