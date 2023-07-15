/**
 *
 */
package mz.co.grocery.persistence.inventory.entity;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class InventoryEntityMapper extends AbstractEntityMapper<InventoryEntity, Inventory> implements EntityMapper<InventoryEntity, Inventory> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<StockInventoryEntity, StockInventory> stockInventoryMapper;

	public InventoryEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper) {
		this.unitMapper = unitMapper;
	}

	@Override
	public InventoryEntity toEntity(final Inventory domain) {

		final InventoryEntity entity = new InventoryEntity();
		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		entity.setInventoryDate(domain.getInventoryDate());
		entity.setInventoryStatus(domain.getInventoryStatus());

		return this.toEntity(entity, domain);
	}

	@Override
	public Inventory toDomain(final InventoryEntity entity) {
		final Inventory domain = new Inventory();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		domain.setInventoryDate(entity.getInventoryDate());
		domain.setInventoryStatus(entity.getInventoryStatus());

		entity.getStockInventories().ifPresent(stockInventories -> stockInventories
				.forEach(stockInventory -> {
					stockInventory.setInventory(null);
					domain.addStockInventory(this.stockInventoryMapper.toDomain(stockInventory));
				}));

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setStockInventoryMapper(final EntityMapper<StockInventoryEntity, StockInventory> stockInventoryMapper) {
		this.stockInventoryMapper = stockInventoryMapper;
	}
}
