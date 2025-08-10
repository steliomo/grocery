/**
 *
 */
package mz.co.grocery.core.application.payment.out;

import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface PaymentPort {

	void paySubscription(UserContext context, SubscriptionDetails subscriptionDetails) throws BusinessException;

	Boolean wasPaymentCompleted(SubscriptionDetails subscriptionDetails) throws BusinessException;

}
