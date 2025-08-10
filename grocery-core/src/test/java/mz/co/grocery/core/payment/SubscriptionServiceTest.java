/**
 *
 */
package mz.co.grocery.core.payment;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.payment.out.PaymentPort;
import mz.co.grocery.core.application.payment.service.SubscriptionService;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.grocery.core.domain.payment.Voucher;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class SubscriptionServiceTest extends AbstractUnitServiceTest {

	@Mock
	private UnitPort unitPort;

	@Captor
	private ArgumentCaptor<Unit> unitCaptor;

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private PaymentPort paymentPort;

	@InjectMocks
	private SubscriptionService subscriptionUseCase;

	@Test
	public void shouldUpdateSubscription() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		final SubscriptionDetails subscriptionDetails = new SubscriptionDetails(Voucher.MONTHLY);
		Mockito.when(this.paymentPort.wasPaymentCompleted(subscriptionDetails)).thenReturn(Boolean.TRUE);

		final UserContext context = this.getUserContext();

		this.subscriptionUseCase.updateSubscription(context, subscriptionDetails);

		Mockito.verify(this.unitPort).updateUnit(ArgumentMatchers.eq(context), this.unitCaptor.capture());

		final Unit unitArgument = this.unitCaptor.getValue();

		Assert.assertEquals(LocalDate.now().plusDays(30), unitArgument.getSubscriptionEndDate());
	}
}
