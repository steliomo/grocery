/**
 *
 */
package mz.co.grocery.core.application.rent.in;

import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface MakeRentPaymentUseCase {

	RentPayment makeRentPayment(UserContext userContext, RentPayment rentPayment) throws BusinessException;
}
