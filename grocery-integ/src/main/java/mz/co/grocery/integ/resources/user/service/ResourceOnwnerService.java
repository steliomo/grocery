/**
 *
 */
package mz.co.grocery.integ.resources.user.service;

import mz.co.grocery.integ.resources.grocery.dto.GroceryUserDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ResourceOnwnerService {

	String createUser(UserContext userContext, GroceryUserDTO groceryUserDTO) throws BusinessException;

}
