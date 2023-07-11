/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ProductUnitEntityMapper extends AbstractEntityMapper<ProductUnitEntity, ProductUnit>
implements EntityMapper<ProductUnitEntity, ProductUnit> {

	@Override
	public ProductUnitEntity toEntity(final ProductUnit domain) {
		final ProductUnitEntity entity = new ProductUnitEntity();

		entity.setProductUnitType(domain.getProductUnitType());
		entity.setUnit(domain.getUnit());

		return this.toEntity(entity, domain);
	}

	@Override
	public ProductUnit toDomain(final ProductUnitEntity entity) {
		final ProductUnit domain = new ProductUnit();

		domain.setProductUnitType(entity.getProductUnitType());
		domain.setUnit(entity.getUnit());

		return this.toDomain(entity, domain);
	}
}
