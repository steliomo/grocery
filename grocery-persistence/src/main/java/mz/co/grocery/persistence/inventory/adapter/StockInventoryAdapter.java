/**
 *
 */
package mz.co.grocery.persistence.inventory.adapter;

import javax.transaction.Transactional;

import mz.co.grocery.core.application.inventory.out.StockInventoryPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.persistence.inventory.entity.StockInventoryEntity;
import mz.co.grocery.persistence.inventory.repository.StockInventoryRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class StockInventoryAdapter extends AbstractService implements StockInventoryPort {

	private StockInventoryRepository repository;

	private EntityMapper<StockInventoryEntity, StockInventory> mapper;

	public StockInventoryAdapter(final StockInventoryRepository repository,
			final EntityMapper<StockInventoryEntity, StockInventory> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public StockInventory createStockInventory(final UserContext userContext, final StockInventory stockInventory)
			throws BusinessException {
		final StockInventoryEntity entity = this.mapper.toEntity(stockInventory);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public StockInventory updateStockInventory(final UserContext userContext, final StockInventory stockInventory)
			throws BusinessException {
		final StockInventoryEntity entity = this.mapper.toEntity(stockInventory);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}
}
