/**
 *
 */
package mz.co.grocery.core.rent.service;

import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RentPaymentService {

	RentPayment makeRentPayment(UserContext userContext, RentPayment rentPayment) throws BusinessException;

}
