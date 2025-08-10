/**
 *
 */
package mz.co.grocery.core.application.payment.service;

import mz.co.grocery.core.application.payment.in.SubscriptionUseCase;
import mz.co.grocery.core.application.payment.out.PaymentPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class SubscriptionService extends AbstractService implements SubscriptionUseCase {

	private UnitPort unitPort;

	private PaymentPort paymentPort;

	public SubscriptionService(final UnitPort unitPort, final PaymentPort paymentPort) {
		this.unitPort = unitPort;
		this.paymentPort = paymentPort;
	}

	@Override
	public SubscriptionDetails updateSubscription(final UserContext userContext, final SubscriptionDetails subscriptionDetails)
			throws BusinessException {

		this.paymentPort.paySubscription(userContext, subscriptionDetails);

		if (!this.paymentPort.wasPaymentCompleted(subscriptionDetails)) {
			throw new BusinessException(subscriptionDetails.getStatusDescription());
		}

		final Unit unit = this.unitPort.findByUuid(subscriptionDetails.getUnitUuid());

		unit.updateSubscription(subscriptionDetails.getDays());

		this.unitPort.updateUnit(userContext, unit);

		return subscriptionDetails;
	}
}
