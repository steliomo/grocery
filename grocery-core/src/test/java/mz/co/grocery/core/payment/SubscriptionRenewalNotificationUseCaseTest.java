/**
 *
 */
package mz.co.grocery.core.payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.application.payment.service.SubscriptionRenewalNotificationService;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SubscriptionRenewalNotificationUseCaseTest extends AbstractUnitServiceTest {

	@InjectMocks
	private SubscriptionRenewalNotificationService subscriptionRenewalNotificationUseCase;

	@Mock
	private UnitPort unitPort;

	@Mock
	private EmailPort emailPort;

	@Test
	public void shouldSendSubscriptionRenewalNotification() throws BusinessException {

		final Unit unit0 = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		final Unit unit1 = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		final Unit unit2 = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);

		unit0.setSubscriptionEndDate(LocalDate.of(2025, 12, 10));
		unit1.setSubscriptionEndDate(LocalDate.of(2025, 12, 7));
		unit2.setSubscriptionEndDate(LocalDate.of(2025, 12, 9));

		final List<Unit> units = new ArrayList<>();
		units.add(unit0);
		units.add(unit1);
		units.add(unit2);

		final LocalDate notificationDate = LocalDate.of(2025, 12, 4);

		Mockito.when(this.unitPort.findUnitsWithSubscriptionActiveToDate(notificationDate)).thenReturn(units);

		final List<Unit> notifiedUnits = this.subscriptionRenewalNotificationUseCase.sendNotification(notificationDate);

		Assert.assertFalse(notifiedUnits.isEmpty());
		Assert.assertEquals(2, notifiedUnits.size());
	}

}
