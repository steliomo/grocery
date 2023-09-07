/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.sale.in.SaleUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.sale.service.InstallmentSaleService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class InstallmentSaleTest extends AbstractUnitServiceTest {

	@Mock
	private StockPort stockPort;

	@Mock
	private InventoryPort inventoryPort;

	@Mock
	private SalePort salePort;

	@Mock
	private SaleItemPort saleItemPort;

	@Mock
	private PaymentUseCase paymentUseCase;

	@InjectMocks
	private final SaleUseCase saleService = new InstallmentSaleService(this.salePort, this.saleItemPort, this.paymentUseCase,
			this.inventoryPort);

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSalewithPendingInventory() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		Mockito.when(this.inventoryPort.fetchInventoryByGroceryAndStatus(sale.getUnit().get(), InventoryStatus.PENDING))
		.thenReturn(EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID));

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSaleWithEmptyItems() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSaleWithoutCustomer() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSaleWithoutDueDate() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);
		sale.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test
	public void shouldRegistInstallmentSale() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID, result -> {

			if (result instanceof Sale) {
				((Sale) result).setDueDate(LocalDate.now().plusDays(30));
			}

			final List<SaleItem> services = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SERVICE);
			services.forEach(service -> {
				if (result instanceof Sale) {
					((Sale) result).addItem(service);
				}
			});
		});

		sale.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));
		sale.setSaleType(SaleType.INSTALLMENT);

		Mockito.when(this.salePort.createSale(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Sale.class))).thenReturn(sale);

		this.saleService.registSale(this.getUserContext(), sale);

		Mockito.verify(this.salePort, Mockito.times(1)).createSale(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Sale.class));
		Mockito.verify(this.saleItemPort, Mockito.times(10)).createSaleItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(SaleItem.class));
		Mockito.verify(this.paymentUseCase, Mockito.times(1)).debitTransaction(this.getUserContext(),
				null);

		Assert.assertNotNull(sale.getCustomer());
		Assert.assertEquals(SaleType.INSTALLMENT, sale.getSaleType());
		Assert.assertEquals(SaleStatus.OPENED, sale.getSaleStatus());
		Assert.assertEquals(BigDecimal.ZERO, sale.getTotalPaid());
		Assert.assertEquals(DeliveryStatus.PENDING, sale.getDeliveryStatus());
	}
}
