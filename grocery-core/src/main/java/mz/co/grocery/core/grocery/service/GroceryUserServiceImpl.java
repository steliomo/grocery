/**
 *
 */
package mz.co.grocery.core.grocery.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.dao.GroceryUserDAO;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(GroceryUserServiceImpl.NAME)
public class GroceryUserServiceImpl extends AbstractService implements GroceryUserService {

	public static final String NAME = "mz.co.grocery.core.grocery.service.GroceryUserServiceImpl";

	@Inject
	private GroceryUserDAO groceryUserDAO;

	@Override
	public GroceryUser createGroceryUser(final UserContext userContext, final GroceryUser groceryUser)
	        throws BusinessException {
		this.groceryUserDAO.create(userContext, groceryUser);
		return groceryUser;
	}
}
