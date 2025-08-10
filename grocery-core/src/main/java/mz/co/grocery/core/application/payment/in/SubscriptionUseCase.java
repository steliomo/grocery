/**
 *
 */
package mz.co.grocery.core.application.payment.in;

import mz.co.grocery.core.domain.payment.Payment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface PaymentUseCase {
	
	Payment updateSubscription(UserContext userContext, Payment payment) throws BusinessException;

	void debitTransaction(UserContext userContext, String unitUuid) throws BusinessException;
}
