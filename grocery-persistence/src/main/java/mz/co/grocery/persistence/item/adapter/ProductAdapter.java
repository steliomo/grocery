/**
 *
 */
package mz.co.grocery.persistence.item.adapter;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ProductEntity;
import mz.co.grocery.persistence.item.entity.ProductEntityMapper;
import mz.co.grocery.persistence.item.repository.ProductRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class ProductAdapter implements ProductPort {

	public static final String NAME = "mz.co.grocery.core.item.service.ProductServiceImpl";

	private ProductRepository repository;

	private EntityMapper<ProductEntity, Product> mapper;

	public ProductAdapter(final ProductRepository repository,
			@BeanQualifier(ProductEntityMapper.NAME) final EntityMapper<ProductEntity, Product> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Product createProduct(final UserContext userContext, final Product product) throws BusinessException {
		final ProductEntity entity = this.mapper.toEntity(product);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Product uppdateProduct(final UserContext userContext, final Product product) throws BusinessException {
		final ProductEntity entity = this.mapper.toEntity(product);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Product removeProduct(final UserContext userContext, final Product product) throws BusinessException {
		product.inactivate();
		return this.uppdateProduct(userContext, product);
	}

	@Override
	public List<Product> findAllProducts() throws BusinessException {
		return this.repository.findAll(EntityStatus.ACTIVE);
	}

	@Override
	public List<Product> findProductByName(final String name) throws BusinessException {
		return this.repository.findByName(name, EntityStatus.ACTIVE);
	}

	@Override
	public Product findProductByUuid(final String uuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(uuid));
	}

	@Override
	public List<Product> findProductsByGrocery(final Unit grocery) throws BusinessException {
		return this.repository.findByGrocery(grocery, EntityStatus.ACTIVE);
	}

	@Override
	public List<Product> findProductsNotInThisGrocery(final Unit grocery) throws BusinessException {
		return this.repository.findNotInThisGrocery(grocery, EntityStatus.ACTIVE);
	}
}
