/**
 *
 */
package mz.co.grocery.core.grocery.service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface GroceryService {
	Grocery createGrocery(UserContext userContext, Grocery grocery) throws BusinessException;
}
