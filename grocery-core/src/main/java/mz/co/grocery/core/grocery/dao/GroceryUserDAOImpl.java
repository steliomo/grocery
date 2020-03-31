/**
 *
 */
package mz.co.grocery.core.grocery.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(GroceryUserDAOImpl.NAME)
public class GroceryUserDAOImpl extends GenericDAOImpl<GroceryUser, Long> implements GroceryUserDAO {

	public static final String NAME = "mz.co.grocery.core.grocery.dao.GroceryUserDAOImpl";

	@Override
	public List<GroceryUser> fetchAllGroceryUsers(final int currentPage, final int maxResult,
	        final EntityStatus entityStatus) throws BusinessException {

		final List<Long> groceryUserIds = this
		        .findByQuery(GroceryUserDAO.QUERY_NAME.findAllIds,
		                new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class)
		        .setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (groceryUserIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(GroceryUserDAO.QUERY_NAME.fetchAll,
		        new ParamBuilder().add("groceryUserIds", groceryUserIds).process());
	}

	@Override
	public GroceryUser fetchByUser(final String user, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(GroceryUserDAO.QUERY_NAME.fetchByUser,
		        new ParamBuilder().add("user", user).add("entityStatus", entityStatus).process());
	}

}