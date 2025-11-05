/**
 *
 */
package mz.co.grocery.core.pos;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.pos.service.PayDeptService;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class PayDebtUseCaseTest extends AbstractUnitServiceTest {

	@InjectMocks
	private PayDeptService payDeptUseCase;

	@Mock
	private SalePort salePort;

	@Mock
	private SalePaymentPort salePaymentPort;

	@Mock
	private Clock clock;

	@Captor
	private ArgumentCaptor<SalePayment> salePaymentCaptor;

	@Captor
	private ArgumentCaptor<Sale> saleCaptor;

	@Test
	public void shouldPayDept() throws BusinessException {

		final Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);

		Mockito.when(this.salePort.findCreditSaleTypeAndPendingSaleStatusSalesByCustomer(customer))
		.thenReturn(EntityFactory.gimme(Sale.class, 5, SaleTemplate.VALID, result -> {
			if (result instanceof Sale) {
				final Sale sale = (Sale) result;
				sale.setTotal(new BigDecimal(2000));
				sale.setTotalPaid(BigDecimal.ZERO);
				sale.setSaleType(SaleType.CREDIT);
				sale.setSaleStatus(SaleStatus.PENDING);
			}
		}));

		final Debt debt = new Debt();
		debt.setAmount(new BigDecimal(10000));
		debt.setCustomer(customer);

		final UserContext context = this.getUserContext();
		final Debt deptPaid = this.payDeptUseCase.pay(context, debt);

		Mockito.verify(this.salePort, Mockito.times(5)).updateSale(ArgumentMatchers.eq(context), this.saleCaptor.capture());
		Mockito.verify(this.salePaymentPort, Mockito.times(5)).createSalePayment(ArgumentMatchers.eq(context), this.salePaymentCaptor.capture());

		for (final SalePayment salePayment : this.salePaymentCaptor.getAllValues()) {
			Mockito.verify(this.salePaymentPort, Mockito.times(1)).createSalePayment(context, salePayment);
		}

		for (final Sale sale : this.saleCaptor.getAllValues()) {
			Mockito.verify(this.salePort, Mockito.times(1)).updateSale(context, sale);
		}

		Assert.assertNotNull(deptPaid);
	}
}
