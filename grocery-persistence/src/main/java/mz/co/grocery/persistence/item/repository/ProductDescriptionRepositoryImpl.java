/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.persistence.item.entity.ProductDescriptionEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class ProductDescriptionRepositoryImpl extends GenericDAOImpl<ProductDescriptionEntity, Long>
implements ProductDescriptionRepository {

	private EntityMapper<ProductDescriptionEntity, ProductDescription> mapper;

	public ProductDescriptionRepositoryImpl(final EntityMapper<ProductDescriptionEntity, ProductDescription> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<ProductDescription> fetchdAll(final int currentPage, final int maxResult,
			final EntityStatus entityStatus) throws BusinessException {
		return this
				.findByQuery(ProductDescriptionRepository.QUERY_NAME.fetchdAll,
						new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList().stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long count(final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(ProductDescriptionRepository.QUERY_NAME.count,
				new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<ProductDescription> fetchByDescription(final String description, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ProductDescriptionRepository.QUERY_NAME.fetchByDescription, new ParamBuilder()
				.add("description", "%" + description + "%").add("entityStatus", entityStatus).process()).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public ProductDescription fetchByUuid(final String productDescriptionUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.mapper.toDomain(this.findSingleByNamedQuery(ProductDescriptionRepository.QUERY_NAME.fetchByUuid, new ParamBuilder()
				.add("productDescriptionUuid", productDescriptionUuid).add("entityStatus", entityStatus).process()));
	}

}
