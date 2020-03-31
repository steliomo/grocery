/**
 *
 */
package mz.co.grocery.integ.resources.user.service;

import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ResourceOnwnerService {

	String createUser(UserContext userContext, UserDTO userDTO) throws BusinessException;

}
