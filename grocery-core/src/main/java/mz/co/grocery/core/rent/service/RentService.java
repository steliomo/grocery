/**
 *
 */
package mz.co.grocery.core.rent.service;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RentService {

	Rent rent(UserContext userContext, Rent rent) throws BusinessException;
}
