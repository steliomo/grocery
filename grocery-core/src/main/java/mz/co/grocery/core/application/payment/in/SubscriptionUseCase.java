/**
 *
 */
package mz.co.grocery.core.application.payment.in;

import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SubscriptionUseCase {

	SubscriptionDetails updateSubscription(UserContext userContext, SubscriptionDetails subscriptionDetails) throws BusinessException;

}
