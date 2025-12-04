/**
 *
 */
package mz.co.grocery.core.application.payment.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.application.payment.in.SubscriptionRenewalNotificationUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.grocery.core.domain.email.EmailType;
import mz.co.grocery.core.domain.payment.NotificationDays;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class SubscriptionRenewalNotificationService implements SubscriptionRenewalNotificationUseCase {

	private UnitPort unitPort;
	private EmailPort emailPort;

	public SubscriptionRenewalNotificationService(final UnitPort unitPort, final EmailPort emailPort) {
		this.unitPort = unitPort;
		this.emailPort = emailPort;
	}

	@Override
	public List<Unit> sendNotification(final LocalDate notificationDate) throws BusinessException {

		final List<Unit> units = this.unitPort.findUnitsWithSubscriptionActiveToDate(notificationDate);

		final List<Unit> notifiedUnits = new ArrayList<>();

		for (final Unit unit : units) {

			final int days = Period.between(notificationDate, unit.getSubscriptionEndDate()).getDays();

			if (days == NotificationDays.THREE_DAYS.getValue() || days == NotificationDays.FIVE_DAYS.getValue()) {

				final EmailDetails email = new EmailDetails(unit, EmailType.SUBSCRIPTION_RENEWAL, Optional.empty());
				email.setParam("subscriptionEndDate", unit.getSubscriptionEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				email.setParam("unitName", unit.getName());

				this.emailPort.send(email);

				notifiedUnits.add(unit);
			}
		}

		return notifiedUnits;
	}
}
