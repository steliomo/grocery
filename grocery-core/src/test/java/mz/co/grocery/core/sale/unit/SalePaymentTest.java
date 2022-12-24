/**
 *
 */
package mz.co.grocery.core.sale.unit;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SalePayment;
import mz.co.grocery.core.sale.model.SaleStatus;
import mz.co.grocery.core.sale.service.SalePaymentDAO;
import mz.co.grocery.core.sale.service.SalePaymentService;
import mz.co.grocery.core.sale.service.SalePaymentServiceImpl;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class SalePaymentTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final SalePaymentService salePaymentService = new SalePaymentServiceImpl();

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private SaleDAO saleDAO;

	@Mock
	private SalePaymentDAO salePaymentDAO;

	@Captor
	private ArgumentCaptor<SalePayment> salePaymentCaptor;

	@Captor
	private ArgumentCaptor<Sale> saleCaptor;

	@Test
	public void shouldPayInstallmentSale() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS, result -> {
			if (result instanceof Sale) {
				((Sale) result).calculateTotal();
				((Sale) result).setTotalPaid(BigDecimal.ZERO);
				((Sale) result).setSaleType(SaleType.INSTALLMENT);
			}
		});
		final BigDecimal paymentValue = new BigDecimal(100);
		final LocalDate PaymenteDate = LocalDate.now();

		final UserContext userContext = this.getUserContext();

		Mockito.when(this.saleDAO.findByUuid(sale.getUuid())).thenReturn(sale);

		this.salePaymentService.payInstallmentSale(userContext, sale.getUuid(), paymentValue, PaymenteDate);

		Mockito.verify(this.saleDAO, Mockito.times(1)).update(userContext, sale);
		Mockito.verify(this.salePaymentDAO).create(ArgumentMatchers.eq(userContext), this.salePaymentCaptor.capture());
		Mockito.verify(this.saleDAO).update(ArgumentMatchers.eq(userContext), this.saleCaptor.capture());

		final SalePayment salePayment = this.salePaymentCaptor.getValue();

		Assert.assertEquals(SaleType.INSTALLMENT, sale.getSaleType());
		Assert.assertEquals(paymentValue, salePayment.getPaymentValue());
		Assert.assertEquals(PaymenteDate, salePayment.getPaymentDate());
		Assert.assertEquals(paymentValue, salePayment.getSale().getTotalPaid());
		Assert.assertEquals(SaleStatus.INCOMPLETE, this.saleCaptor.getValue().getSaleStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotPayInstallmentForCashSale() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID, result -> {
			if (result instanceof Sale) {
				((Sale) result).setSaleType(SaleType.CASH);
			}
		});
		final BigDecimal paymentValue = new BigDecimal(100);
		final LocalDate PaymenteDate = LocalDate.now();

		Mockito.when(this.saleDAO.findByUuid(sale.getUuid())).thenReturn(sale);

		Mockito.when(this.translator.getTranslation("cannot.perform.payment.for.non.installment.sale"))
		.thenReturn("Cannot perform payment for non installment sale");

		this.salePaymentService.payInstallmentSale(this.getUserContext(), sale.getUuid(), paymentValue, PaymenteDate);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotPayInstallmentForHigherThanTheExpectedPaymentValue() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS, result -> {
			if (result instanceof Sale) {
				((Sale) result).calculateTotal();
				((Sale) result).setTotalPaid(BigDecimal.ZERO);
				((Sale) result).setSaleType(SaleType.INSTALLMENT);
			}
		});
		final BigDecimal paymentValue = sale.getRemainingPayment().add(new BigDecimal(100));
		final LocalDate PaymenteDate = LocalDate.now();

		Mockito.when(this.saleDAO.findByUuid(sale.getUuid())).thenReturn(sale);

		Mockito.when(this.translator.getTranslation("the.payment.value.is.higher.than.expected"))
		.thenReturn("The payment value is higher than expected. Was expected:" + sale.getRemainingPayment().toString() + " but is"
				+ paymentValue.toString());

		this.salePaymentService.payInstallmentSale(this.getUserContext(), sale.getUuid(), paymentValue, PaymenteDate);
	}

}
