/**
 *
 */
package mz.co.grocery.core.grocery.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(GroceryDAOImpl.NAME)
public class GroceryDAOImpl extends GenericDAOImpl<Grocery, Long> implements GroceryDAO {

	public static final String NAME = "mz.co.grocery.core.grocery.dao.GroceryDAOImpl";

	@Override
	public List<Grocery> findAll(final int currentPage, final int maxReult, final EntityStatus entityStatus)
			throws BusinessException {

		final List<Long> groceryIds = this
				.findByQuery(GroceryDAO.QUERY_NAME.findAllIds,
						new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class)
				.setFirstResult(currentPage * maxReult).setMaxResults(maxReult).getResultList();

		if (groceryIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(GroceryDAO.QUERY_NAME.findAll,
				new ParamBuilder().add("groceryIds", groceryIds).process());
	}

	@Override
	public List<Grocery> findByName(final String unitName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(GroceryDAO.QUERY_NAME.findByName,
				new ParamBuilder().add("unitName", "%" + unitName + "%").add("entityStatus", entityStatus).process());
	}

}
