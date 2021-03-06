/**
 *
 */
package mz.co.grocery.core.item.dao;

import java.util.List;

import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductUnitDAO extends GenericDAO<ProductUnit, Long> {

	class QUERY {
		public static final String findAll = "SELECT pu FROM ProductUnit pu WHERE pu.entityStatus = :entityStatus ORDER BY pu.unit, pu.productUnitType";
	}

	class QUERY_NAME {
		public static final String findAll = "ProductUnit.findAll";
	}

	List<ProductUnit> findAll(EntityStatus entityStatus) throws BusinessException;
}
