/**
 *
 */
package mz.co.grocery.core.pos;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.pos.in.CancelTableUseCase;
import mz.co.grocery.core.application.pos.service.CancelTableService;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class CancelTableUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private SalePort salePort;

	@Mock
	private StockPort stockPort;

	@Mock
	private PaymentUseCase paymentUseCase;

	@InjectMocks
	private CancelTableUseCase cancelTableUseCase = new CancelTableService(this.salePort, this.stockPort, this.paymentUseCase);

	@Test
	public void shouldCancelTableWithoutItensUseCase() throws BusinessException {

		final Sale table = new Sale();
		table.setUnit(EntityFactory.gimme(Unit.class, UnitTemplate.VALID));
		table.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		Mockito.when(this.salePort.updateSale(this.getUserContext(), table)).thenReturn(table);
		Mockito.when(this.salePort.findByUuid(table.getUuid())).thenReturn(table);

		this.cancelTableUseCase.cancel(this.getUserContext(), table);

		Assert.assertEquals(SaleStatus.CANCELLED, table.getSaleStatus());
	}

	@Test
	public void shouldCancelTableWithItensUseCase() throws BusinessException {

		final Sale table = new Sale();
		table.setUnit(EntityFactory.gimme(Unit.class, UnitTemplate.VALID));
		table.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		final SaleItem productSaleItem = new SaleItem();
		final SaleItem serviceSaleItem = new SaleItem();

		final Stock stock = new Stock();
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(150));
		stock.setQuantity(BigDecimal.ZERO);

		final ServiceItem serviceItem = new ServiceItem();
		serviceItem.setSalePrice(new BigDecimal(10));

		serviceSaleItem.setServiceItem(serviceItem);

		productSaleItem.setStock(stock);

		productSaleItem.setQuantity(new BigDecimal(10));
		productSaleItem.setDeliveredQuantity(new BigDecimal(10));
		productSaleItem.setSaleItemValue(new BigDecimal(100));
		productSaleItem.setDiscount(BigDecimal.ZERO);

		table.addItem(productSaleItem);
		table.addItem(serviceSaleItem);

		Mockito.when(this.stockPort.findStockByUuid(productSaleItem.getStock().get().getUuid())).thenReturn(stock);
		Mockito.when(this.salePort.findByUuid(table.getUuid())).thenReturn(table);

		final UserContext context = this.getUserContext();

		this.cancelTableUseCase.cancel(context, table);

		Mockito.verify(this.salePort, Mockito.times(1)).findByUuid(table.getUuid());
		Mockito.verify(this.stockPort, Mockito.times(1)).findStockByUuid(productSaleItem.getStock().get().getUuid());
		Mockito.verify(this.stockPort, Mockito.times(1)).updateStock(context, stock);
		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(context, table);

		Assert.assertEquals(SaleStatus.CANCELLED, table.getSaleStatus());
	}
}
