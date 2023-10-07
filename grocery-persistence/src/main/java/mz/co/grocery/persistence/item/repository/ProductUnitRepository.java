/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import mz.co.grocery.persistence.item.entity.ProductUnitEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductUnitRepository extends GenericDAO<ProductUnitEntity, Long> {

	class QUERY {
		public static final String findAll = "SELECT pu FROM ProductUnitEntity pu WHERE pu.entityStatus = :entityStatus ORDER BY pu.unit, pu.productUnitType";
	}

	class QUERY_NAME {
		public static final String findAll = "ProductUnitEntity.findAll";
	}

	List<ProductUnitEntity> findAll(EntityStatus entityStatus) throws BusinessException;
}
