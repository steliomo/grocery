/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.product.model.Product;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ProductDAOImpl.NAME)
public class ProductDAOImpl extends GenericDAOImpl<Product, Long> implements ProductDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ProductDAOImpl";

	@Override
	public List<Product> findAll(final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ProductDAO.QUERY_NAME.findAll,
				new ParamBuilder().add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Product> findByName(final String name, final EntityStatus entityStatus) throws BusinessException {

		return this.findByNamedQuery(ProductDAO.QUERY_NAME.findByName,
				new ParamBuilder().add("name", "%" + name + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Product> findByGrocery(final Grocery grocery, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ProductDAO.QUERY_NAME.findByGrocery,
				new ParamBuilder().add("groceryUuid", grocery.getUuid()).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Product> findNotInThisGrocery(final Grocery grocery, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(ProductDAO.QUERY_NAME.findNotInThisGrocery,
				new ParamBuilder().add("groceryUuid", grocery.getUuid()).add("entityStatus", entityStatus).process());
	}
}
