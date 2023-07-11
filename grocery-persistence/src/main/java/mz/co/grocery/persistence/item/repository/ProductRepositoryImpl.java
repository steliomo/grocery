/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ProductEntity;
import mz.co.grocery.persistence.item.entity.ProductEntityMapper;
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
public class ProductRepositoryImpl extends GenericDAOImpl<ProductEntity, Long> implements ProductRepository {

	private EntityMapper<ProductEntity, Product> mapper;

	public ProductRepositoryImpl(final @BeanQualifier(ProductEntityMapper.NAME) EntityMapper<ProductEntity, Product> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<Product> findAll(final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ProductRepository.QUERY_NAME.findAll,
				new ParamBuilder().add("entityStatus", entityStatus).process()).stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Product> findByName(final String name, final EntityStatus entityStatus) throws BusinessException {

		return this.findByNamedQuery(ProductRepository.QUERY_NAME.findByName,
				new ParamBuilder().add("name", "%" + name + "%").add("entityStatus", entityStatus).process()).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Product> findByGrocery(final Unit grocery, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ProductRepository.QUERY_NAME.findByGrocery,
				new ParamBuilder().add("groceryUuid", grocery.getUuid()).add("entityStatus", entityStatus).process()).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Product> findNotInThisGrocery(final Unit grocery, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ProductRepository.QUERY_NAME.findNotInThisGrocery,
				new ParamBuilder().add("groceryUuid", grocery.getUuid()).add("entityStatus", entityStatus).process()).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}
}
