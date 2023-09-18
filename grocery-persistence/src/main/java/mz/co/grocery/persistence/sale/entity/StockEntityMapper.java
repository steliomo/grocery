/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.item.entity.ProductDescriptionEntity;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class StockEntityMapper extends AbstractEntityMapper<StockEntity, Stock> implements EntityMapper<StockEntity, Stock> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<ProductDescriptionEntity, ProductDescription> productDescriptionMapper;

	public StockEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper,
			final EntityMapper<ProductDescriptionEntity, ProductDescription> productDescriptionMapper) {
		this.unitMapper = unitMapper;
		this.productDescriptionMapper = productDescriptionMapper;
	}

	@Override
	public StockEntity toEntity(final Stock domain) {
		final StockEntity entity = new StockEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getProductDescription()
		.ifPresent(productDescription -> entity.setProductDescription(this.productDescriptionMapper.toEntity(productDescription)));

		entity.setPurchasePrice(domain.getPurchasePrice());
		entity.setSalePrice(domain.getSalePrice());
		entity.setQuantity(domain.getQuantity());
		entity.setExpireDate(domain.getExpireDate());
		entity.setMinimumStock(domain.getMinimumStock());
		entity.setStockStatus(domain.getStockStatus());
		entity.setInventoryDate(domain.getInventoryDate());
		entity.setInventoryQuantity(domain.getInventoryQuantity());
		entity.setStockUpdateDate(domain.getStockUpdateDate());
		entity.setStockUpdateQuantity(domain.getStockUpdateQuantity());
		entity.setProductStockStatus(domain.getProductStockStatus());
		entity.setRentPrice(domain.getRentPrice());
		entity.setUnitPerM2(domain.getUnitPerM2());

		return this.toEntity(entity, domain);
	}

	@Override
	public Stock toDomain(final StockEntity entity) {
		final Stock domain = new Stock();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		entity.getProductDescription()
		.ifPresent(productDescription -> domain.setProductDescription(this.productDescriptionMapper.toDomain(productDescription)));

		domain.setPurchasePrice(entity.getPurchasePrice());
		domain.setSalePrice(entity.getSalePrice());
		domain.setQuantity(entity.getQuantity());
		domain.setExpireDate(entity.getExpireDate());
		domain.setMinimumStock(entity.getMinimumStock());
		domain.setStockStatus(entity.getStockStatus());
		domain.setInventoryDate(entity.getInventoryDate());
		domain.setInventoryQuantity(entity.getInventoryQuantity());
		domain.setStockUpdateDate(entity.getStockUpdateDate());
		domain.setStockUpdateQuantity(entity.getStockUpdateQuantity());
		domain.setProductStockStatus(entity.getProductStockStatus());
		domain.setRentPrice(entity.getRentPrice());
		domain.setUnitPerM2(entity.getUnitPerM2());

		return this.toDomain(entity, domain);
	}
}
