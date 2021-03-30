/**
 *
 */
package mz.co.grocery.core.item.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ProductUnitDAOImpl.NAME)
public class ProductUnitDAOImpl extends GenericDAOImpl<ProductUnit, Long> implements ProductUnitDAO {

	public static final String NAME = "mz.co.grocery.core.item.dao.ProductUnitDAOImpl";

	@Override
	public List<ProductUnit> findAll(final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ProductUnitDAO.QUERY_NAME.findAll,
				new ParamBuilder().add("entityStatus", entityStatus).process());
	}

}
