/**
 *
 */
package mz.co.grocery.persistence.item.adapter;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.persistence.item.entity.ProductUnitEntity;
import mz.co.grocery.persistence.item.repository.ProductUnitRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class ProductUnitAdapter implements ProductUnitPort {

	private ProductUnitRepository repository;

	private EntityMapper<ProductUnitEntity, ProductUnit> mapper;

	public ProductUnitAdapter(final ProductUnitRepository repository, final EntityMapper<ProductUnitEntity, ProductUnit> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public ProductUnit createProductUnit(final UserContext userContext, final ProductUnit productUnit)
			throws BusinessException {
		final ProductUnitEntity entity = this.mapper.toEntity(productUnit);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public ProductUnit updateProductUnit(final UserContext userContext, final ProductUnit productUnit)
			throws BusinessException {
		final ProductUnitEntity entity = this.mapper.toEntity(productUnit);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public ProductUnit removeProductUnit(final UserContext userContext, final ProductUnit productUnit)
			throws BusinessException {

		productUnit.inactivate();

		return this.updateProductUnit(userContext, productUnit);
	}

	@Override
	public List<ProductUnit> findAllProductUnits() throws BusinessException {
		return this.repository.findAll(EntityStatus.ACTIVE);
	}

	@Override
	public ProductUnit findProductUnitByUuid(final String productUnitUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(productUnitUuid));
	}
}
