/**
 *
 */
package mz.co.grocery.persistence.unit;

import mz.co.grocery.core.application.payment.out.PaymentPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class WalletPaymentDummy implements PaymentPort {

	@Override
	public void paySubscription(final UserContext context, final SubscriptionDetails subscriptionDetails) throws BusinessException {
	}

	@Override
	public Boolean wasPaymentCompleted(final SubscriptionDetails subscriptionDetails) throws BusinessException {
		return null;
	}
}
