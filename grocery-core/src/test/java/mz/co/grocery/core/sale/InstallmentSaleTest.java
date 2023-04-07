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

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.dao.SaleItemDAO;
import mz.co.grocery.core.sale.model.DeliveryStatus;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.model.SaleStatus;
import mz.co.grocery.core.sale.service.InstallmentSaleServiceImpl;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class InstallmentSaleTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final SaleService saleService = new InstallmentSaleServiceImpl();

	@Mock
	private StockDAO stockDAO;

	@Mock
	private InventoryDAO inventoryDAO;

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private SaleDAO saleDAO;

	@Mock
	private SaleItemDAO saleItemDAO;

	@Mock
	private PaymentService paymentService;

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSalewithPendingInventory() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		Mockito.when(this.inventoryDAO.fetchByGroceryAndStatus(sale.getGrocery(), InventoryStatus.PENDING, EntityStatus.ACTIVE))
		.thenReturn(EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID));

		Mockito.when(this.translator.getTranslation("cannot.registe.sale.with.pendind.inventory"))
		.thenReturn("Cannot regist a sale with a pending inventory!");

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSaleWithEmptyItems() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		Mockito.when(this.translator.getTranslation("cannot.create.sale.without.items"))
		.thenReturn("Cannot regist a sale with empty items!");

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSaleWithoutCustomer() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);

		Mockito.when(this.translator.getTranslation("installment.sale.must.have.customer"))
		.thenReturn("Cannot regist a sale without a customer!");

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistInstallmentSaleWithoutDueDate() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);
		sale.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		Mockito.when(this.translator.getTranslation("installment.sale.due.date.must.be.specified"))
		.thenReturn("Cannot regist a sale without due date");

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

		this.saleService.registSale(this.getUserContext(), sale);

		Mockito.verify(this.saleDAO, Mockito.times(1)).create(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Sale.class));
		Mockito.verify(this.saleItemDAO, Mockito.times(10)).create(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(SaleItem.class));
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(this.getUserContext(),
				null);

		Assert.assertNotNull(sale.getCustomer());
		Assert.assertEquals(SaleType.INSTALLMENT, sale.getSaleType());
		Assert.assertEquals(SaleStatus.PENDING, sale.getSaleStatus());
		Assert.assertEquals(BigDecimal.ZERO, sale.getTotalPaid());
		Assert.assertEquals(DeliveryStatus.PENDING, sale.getDeliveryStatus());
	}

}
