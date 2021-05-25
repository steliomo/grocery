/**
 *
 */
package mz.co.grocery.core.payment.service;

import mz.co.grocery.core.payment.model.Payment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface PaymentService {
	Payment updateSubscription(UserContext userContext, Payment payment) throws BusinessException;

	void debitTransaction(UserContext userContext, String unitUuid) throws BusinessException;
}
