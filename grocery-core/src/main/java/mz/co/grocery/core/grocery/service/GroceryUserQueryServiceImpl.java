/**
 *
 */
package mz.co.grocery.core.grocery.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.dao.GroceryUserDAO;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UnitDetail;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(GroceryUserQueryServiceImpl.NAME)
public class GroceryUserQueryServiceImpl implements GroceryUserQueryService {

	@Inject
	private GroceryUserDAO groceryUserDAO;

	public static final String NAME = "mz.co.grocery.core.grocery.service.GroceryUserQueryServiceImpl";

	@Override
	public List<GroceryUser> fetchAllGroceryUsers(final int currentPage, final int maxResult) throws BusinessException {
		return this.groceryUserDAO.fetchAllGroceryUsers(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long count() throws BusinessException {
		return this.groceryUserDAO.count();
	}

	@Override
	public GroceryUser fetchGroceryUserByUser(final String user) throws BusinessException {
		return this.groceryUserDAO.fetchByUser(user, EntityStatus.ACTIVE);
	}

	@Override
	public UnitDetail findUnitDetailsByUuid(final String unitUuid) throws BusinessException {
		return this.groceryUserDAO.findUnitDetailByUuid(unitUuid, EntityStatus.ACTIVE);
	}
}
