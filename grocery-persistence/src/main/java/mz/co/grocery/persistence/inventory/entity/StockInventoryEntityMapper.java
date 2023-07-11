/**
 *
 */
package mz.co.grocery.persistence.inventory.entity;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class StockInventoryEntityMapper extends AbstractEntityMapper<StockInventoryEntity, StockInventory>
implements EntityMapper<StockInventoryEntity, StockInventory> {

	private EntityMapper<InventoryEntity, Inventory> inventoryMapper;

	private EntityMapper<StockEntity, Stock> stockMapper;

	@Override
	public StockInventoryEntity toEntity(final StockInventory domain) {
		final StockInventoryEntity entity = new StockInventoryEntity();

		domain.getInventory().ifPresent(inventory -> entity.setInventory(this.inventoryMapper.toEntity(inventory)));
		domain.getStock().ifPresent(sotck -> entity.setStock(this.stockMapper.toEntity(sotck)));

		entity.setFisicalInventory(domain.getFisicalInventory());

		return this.toEntity(entity, domain);
	}

	@Override
	public StockInventory toDomain(final StockInventoryEntity entity) {
		final StockInventory domain = new StockInventory();

		entity.getInventory().ifPresent(inventory -> domain.setInventory(this.inventoryMapper.toDomain(inventory)));
		entity.getStock().ifPresent(sotck -> domain.setStock(this.stockMapper.toDomain(sotck)));

		domain.setFisicalInventory(entity.getFisicalInventory());

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setInventoryMapper(final EntityMapper<InventoryEntity, Inventory> inventoryMapper) {
		this.inventoryMapper = inventoryMapper;
	}

	@Inject
	public void setStockMapper(final EntityMapper<StockEntity, Stock> stockMapper) {
		this.stockMapper = stockMapper;
	}
}
