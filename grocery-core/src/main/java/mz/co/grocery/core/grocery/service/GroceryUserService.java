/**
 *
 */
package mz.co.grocery.core.grocery.service;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface GroceryUserService {

	GroceryUser createGroceryUser(UserContext userContext, GroceryUser groceryUser) throws BusinessException;
}
