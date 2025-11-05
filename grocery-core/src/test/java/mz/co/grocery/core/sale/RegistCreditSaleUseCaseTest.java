/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.service.RegistCreditSaleService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RegistCreditSaleUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private SalePort salePort;

	@InjectMocks
	private RegistCreditSaleService registCreditSaleUseCase;

	@Test
	public void shouldRegistCreditSale() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);
		sale.setTotal(BigDecimal.ONE);
		sale.setTotalPaid(BigDecimal.ZERO);

		Mockito.when(this.salePort.findByUuid(sale.getUuid())).thenReturn(sale);

		final Sale registedSale = this.registCreditSaleUseCase.regist(this.getUserContext(), sale.getUuid());

		Assert.assertEquals(SaleType.CREDIT, registedSale.getSaleType());
		Assert.assertEquals(SaleStatus.PENDING, registedSale.getSaleStatus());
	}
}
