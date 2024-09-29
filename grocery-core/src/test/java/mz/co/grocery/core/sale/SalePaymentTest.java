/**
 *
 */
package mz.co.grocery.core.sale;

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

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.sale.in.SalePaymentUseCase;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.service.SalePaymentService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class SalePaymentTest extends AbstractUnitServiceTest {

	@Mock
	private SalePort salePort;

	@Mock
	private SalePaymentPort salePaymentPort;

	@Mock
	PaymentUseCase paymentUseCase;

	@InjectMocks
	private final SalePaymentUseCase salePaymentService = new SalePaymentService(this.salePort, this.salePaymentPort, this.paymentUseCase);

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

		Mockito.when(this.salePort.fetchByUuid(sale.getUuid())).thenReturn(sale);

		this.salePaymentService.payInstallmentSale(userContext, sale.getUuid(), paymentValue, PaymenteDate);

		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(userContext, sale);
		Mockito.verify(this.salePaymentPort).createSalePayment(ArgumentMatchers.eq(userContext), this.salePaymentCaptor.capture());
		Mockito.verify(this.salePort).updateSale(ArgumentMatchers.eq(userContext), this.saleCaptor.capture());
		final SalePayment salePayment = this.salePaymentCaptor.getValue();

		Assert.assertEquals(SaleType.INSTALLMENT, sale.getSaleType());
		Assert.assertEquals(paymentValue, salePayment.getPaymentValue());
		Assert.assertEquals(PaymenteDate, salePayment.getPaymentDate());
		Assert.assertEquals(paymentValue, salePayment.getSale().get().getTotalPaid());
		Assert.assertEquals(SaleStatus.IN_PROGRESS, this.saleCaptor.getValue().getSaleStatus());
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

		Mockito.when(this.salePort.fetchByUuid(sale.getUuid())).thenReturn(sale);

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

		Mockito.when(this.salePort.fetchByUuid(sale.getUuid())).thenReturn(sale);

		this.salePaymentService.payInstallmentSale(this.getUserContext(), sale.getUuid(), paymentValue, PaymenteDate);
	}
}
