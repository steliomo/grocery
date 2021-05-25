/**
 *
 */
package mz.co.grocery.core.payment.unit;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.grocery.dao.GroceryDAO;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.payment.model.Payment;
import mz.co.grocery.core.payment.model.Voucher;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.payment.service.PaymentServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class PaymentServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final PaymentService paymentService = new PaymentServiceImpl();

	@Mock
	private GroceryDAO unitDAO;

	@Captor
	private ArgumentCaptor<Grocery> unitCaptor;

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateSubscription() throws BusinessException {

		final Payment payment = new Payment();
		payment.setStatus("	INS-1");
		payment.setStatusDescription("Internal Error");

		this.paymentService.updateSubscription(this.getUserContext(), payment);
	}

	@Test
	public void shouldUpdateSubscription() throws BusinessException {

		final Grocery unit = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);
		unit.setBalance(new BigDecimal(-10));
		Mockito.when(this.unitDAO.findByUuid(unit.getUuid())).thenReturn(unit);

		final Payment payment = new Payment(Voucher.FIVE_HUNDRED);
		payment.setStatus("INS-0");
		payment.setStatusDescription("Request processed successfully");

		final UserContext context = this.getUserContext();

		this.paymentService.updateSubscription(context, payment);
		Mockito.verify(this.unitDAO).update(ArgumentMatchers.eq(context), this.unitCaptor.capture());

		final Grocery unitArgument = this.unitCaptor.getValue();

		Assert.assertEquals(payment.getVoucherValue(), unitArgument.getBalance());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotDebitTransation() throws BusinessException {

		final Grocery unit = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);
		unit.setBalance(new BigDecimal(-10));
		Mockito.when(this.unitDAO.findByUuid(unit.getUuid())).thenReturn(unit);

		this.paymentService.debitTransaction(this.getUserContext(), unit.getUuid());
	}

	@Test
	public void shouldDebitTransation() throws BusinessException {

		final Grocery unit = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);
		unit.setBalance(new BigDecimal(10));

		Mockito.when(this.unitDAO.findByUuid(unit.getUuid())).thenReturn(unit);

		final UserContext context = this.getUserContext();

		this.paymentService.debitTransaction(context, unit.getUuid());

		Mockito.verify(this.unitDAO).update(ArgumentMatchers.eq(context), this.unitCaptor.capture());
		final Grocery unitArgument = this.unitCaptor.getValue();

		Assert.assertEquals(unit.getBalance(), unitArgument.getBalance());
	}
}
