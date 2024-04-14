/**
 *
 */
package mz.co.grocery.persistence.item.adapter;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.persistence.item.entity.ProductDescriptionEntity;
import mz.co.grocery.persistence.item.repository.ProductDescriptionRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class ProductDescriptionAdapter implements ProductDescriptionPort {

	private ProductDescriptionRepository repository;

	private EntityMapper<ProductDescriptionEntity, ProductDescription> mapper;

	public ProductDescriptionAdapter(final ProductDescriptionRepository repository,
			final EntityMapper<ProductDescriptionEntity, ProductDescription> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public ProductDescription createProductDescription(final UserContext userContext,
			final ProductDescription productDescription) throws BusinessException {
		final ProductDescriptionEntity entity = this.mapper.toEntity(productDescription);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public ProductDescription updateProductDescription(final UserContext userContext,
			final ProductDescription productDescription) throws BusinessException {
		final ProductDescriptionEntity entity = this.mapper.toEntity(productDescription);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<ProductDescription> fetchdAllProductDescriptions(final int currentPage, final int maxResult)
			throws BusinessException {
		return this.repository.fetchdAll(currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countProductDescriptions() throws BusinessException {
		return this.repository.count(EntityStatus.ACTIVE);
	}

	@Override
	public List<ProductDescription> fetchProductDescriptionByDescription(final String description)
			throws BusinessException {
		return this.repository.fetchByDescription(description, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public ProductDescription fetchProductDescriptionByUuid(final String productDescriptionUuid)
			throws BusinessException {
		return this.mapper.toDomain(this.repository.fetchByUuid(productDescriptionUuid, EntityStatus.ACTIVE));
	}

	@Override
	public ProductDescription removeProductDescription(final UserContext userContext, final ProductDescription productDescription)
			throws BusinessException {
		productDescription.inactivate();
		return this.updateProductDescription(userContext, productDescription);
	}
}
