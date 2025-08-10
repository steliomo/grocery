/**
 *
 */
package mz.co.grocery.core.payment;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.payment.service.PaymentService;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.payment.Payment;
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
public class PaymentServiceTest extends AbstractUnitServiceTest {

	@Mock
	private UnitPort unitPort;

	@Captor
	private ArgumentCaptor<Unit> unitCaptor;

	@Mock
	private ApplicationTranslator translator;

	@InjectMocks
	private final PaymentUseCase paymentUseCase = new PaymentService(this.unitPort);

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateSubscription() throws BusinessException {

		final Payment payment = new Payment();
		payment.setStatus("	INS-1");
		payment.setStatusDescription("Internal Error");

		this.paymentUseCase.updateSubscription(this.getUserContext(), payment);
	}

	@Test
	public void shouldUpdateSubscription() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit.setBalance(new BigDecimal(-10));
		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		final Payment payment = new Payment(Voucher.FIVE_HUNDRED);
		payment.setStatus("INS-0");
		payment.setStatusDescription("Request processed successfully");

		final UserContext context = this.getUserContext();

		this.paymentUseCase.updateSubscription(context, payment);
		Mockito.verify(this.unitPort).updateUnit(ArgumentMatchers.eq(context), this.unitCaptor.capture());

		final Unit unitArgument = this.unitCaptor.getValue();

		Assert.assertEquals(payment.getVoucherValue(), unitArgument.getBalance());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotDebitTransation() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit.setBalance(new BigDecimal(-10));
		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		this.paymentUseCase.debitTransaction(this.getUserContext(), unit.getUuid());
	}

	@Test
	public void shouldDebitTransation() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit.setBalance(new BigDecimal(10));

		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		final UserContext context = this.getUserContext();

		this.paymentUseCase.debitTransaction(context, unit.getUuid());

		Mockito.verify(this.unitPort).updateUnit(ArgumentMatchers.eq(context), this.unitCaptor.capture());
		final Unit unitArgument = this.unitCaptor.getValue();

		Assert.assertEquals(unit.getBalance(), unitArgument.getBalance());
	}
}
