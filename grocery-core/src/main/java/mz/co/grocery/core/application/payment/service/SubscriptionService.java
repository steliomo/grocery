/**
 *
 */
package mz.co.grocery.core.application.payment.service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.application.payment.in.SubscriptionUseCase;
import mz.co.grocery.core.application.payment.out.PaymentPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.grocery.core.domain.email.EmailType;
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

	private EmailPort emailPort;

	private Clock clock;

	public SubscriptionService(final UnitPort unitPort, final PaymentPort paymentPort, final EmailPort emailPort, final Clock clock) {
		this.unitPort = unitPort;
		this.paymentPort = paymentPort;
		this.emailPort = emailPort;
		this.clock = clock;
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

		final EmailDetails email = new EmailDetails(unit, EmailType.SUBSCRIPTION_PAYMENT_RECEIPT, Optional.empty());

		final DecimalFormat formatter = new DecimalFormat("#,###.00 MT");

		email.setParam("customerName", userContext.getFullName());
		email.setParam("unitName", unit.getName());
		email.setParam("amount", formatter.format(subscriptionDetails.getTotal()));
		email.setParam("paymentDate", this.clock.todayDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		email.setParam("subscriptionEndDate", unit.getSubscriptionEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		this.emailPort.send(email);

		return subscriptionDetails;
	}
}
