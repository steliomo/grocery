/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component(ProductEntityMapper.NAME)
public class ProductEntityMapper extends AbstractEntityMapper<ProductEntity, Product> implements EntityMapper<ProductEntity, Product> {

	public static final String NAME = "mz.co.grocery.persistence.item.entity.ProductEntityMapper";

	@Override
	public ProductEntity toEntity(final Product domain) {
		final ProductEntity entity = new ProductEntity();
		entity.setName(domain.getName());

		return this.toEntity(entity, domain);
	}

	@Override
	public Product toDomain(final ProductEntity entity) {
		final Product domain = new Product();
		domain.setName(entity.getName());

		return this.toDomain(entity, domain);
	}
}
