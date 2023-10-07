/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.item.entity.ProductUnitEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class ProductUnitRepositoryImpl extends GenericDAOImpl<ProductUnitEntity, Long> implements ProductUnitRepository {

	@Override
	public List<ProductUnitEntity> findAll(final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ProductUnitRepository.QUERY_NAME.findAll,
				new ParamBuilder().add("entityStatus", entityStatus).process());
	}
}
