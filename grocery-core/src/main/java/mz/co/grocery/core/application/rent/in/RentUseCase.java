/**
 *
 */
package mz.co.grocery.core.application.rent.in;

import mz.co.grocery.core.domain.rent.Rent;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RentUseCase {

	Rent rent(UserContext userContext, Rent rent) throws BusinessException;

}
