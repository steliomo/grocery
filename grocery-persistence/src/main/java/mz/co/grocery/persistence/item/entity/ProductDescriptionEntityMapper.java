/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ProductDescriptionEntityMapper extends AbstractEntityMapper<ProductDescriptionEntity, ProductDescription>
implements EntityMapper<ProductDescriptionEntity, ProductDescription> {

	private EntityMapper<ProductEntity, Product> productMapper;

	private EntityMapper<ProductUnitEntity, ProductUnit> productUnitMapper;

	public ProductDescriptionEntityMapper(final EntityMapper<ProductEntity, Product> productMapper,
			final EntityMapper<ProductUnitEntity, ProductUnit> productUnitMapper) {
		this.productMapper = productMapper;
		this.productUnitMapper = productUnitMapper;
	}

	@Override
	public ProductDescriptionEntity toEntity(final ProductDescription domain) {
		final ProductDescriptionEntity entity = new ProductDescriptionEntity();

		domain.getProduct().ifPresent(product -> entity.setProduct(this.productMapper.toEntity(product)));
		entity.setDescription(domain.getDescription());
		domain.getProductUnit().ifPresent(ProductUnit -> entity.setProductUnit(this.productUnitMapper.toEntity(ProductUnit)));

		return this.toEntity(entity, domain);
	}

	@Override
	public ProductDescription toDomain(final ProductDescriptionEntity entity) {
		final ProductDescription domain = new ProductDescription();

		entity.getProduct().ifPresent(product -> domain.setProduct(this.productMapper.toDomain(product)));
		domain.setDescription(entity.getDescription());
		entity.getProductUnit().ifPresent(productUnit -> domain.setProductUnit(this.productUnitMapper.toDomain(productUnit)));

		return this.toDomain(entity, domain);
	}
}
