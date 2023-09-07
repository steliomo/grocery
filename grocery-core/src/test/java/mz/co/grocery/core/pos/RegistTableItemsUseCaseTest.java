/**
 *
 */
package mz.co.grocery.core.pos;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.pos.in.RegistTableItemsUseCase;
import mz.co.grocery.core.application.pos.service.RegistTableItemsService;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
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

public class RegistTableItemsUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private SalePort salePort;

	@Mock
	private SaleItemPort saleItemPort;

	@InjectMocks
	private RegistTableItemsUseCase registTableItemsUseCase = new RegistTableItemsService(this.saleItemPort, this.salePort);

	private Sale sale;

	private UserContext context;

	@Before
	public void setup() throws BusinessException {

		this.context = this.getUserContext();

		this.sale = new Sale();
		this.sale.setUnit(EntityFactory.gimme(Unit.class, UnitTemplate.VALID));
		this.sale.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));
	}

	@Test
	public void shouldRegistNewTableProducts() throws BusinessException {

		final SaleItem saleItem = new SaleItem();

		final Stock stock = new Stock();
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(150));

		saleItem.setStock(stock);

		saleItem.setQuantity(new BigDecimal(10));
		saleItem.setDeliveredQuantity(new BigDecimal(10));
		saleItem.setSaleItemValue(new BigDecimal(100));
		saleItem.setDiscount(BigDecimal.ZERO);

		this.sale.addItem(saleItem);

		Mockito.when(this.salePort.fetchByUuid(this.sale.getUuid())).thenReturn(this.sale);

		this.registTableItemsUseCase.registTableItems(this.context, this.sale);

		Mockito.verify(this.saleItemPort, Mockito.times(1)).createSaleItem(this.context, saleItem);
		Mockito.verify(this.saleItemPort, Mockito.times(1)).findBySaleAndProductUuid(this.sale.getUuid(), saleItem.getStock().get().getUuid());
		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(this.context, this.sale);

		Assert.assertNotEquals(this.sale.getTotal(), BigDecimal.ZERO);
		Assert.assertEquals(this.sale.getSaleStatus(), SaleStatus.IN_PROGRESS);
	}

	@Test
	public void shouldRegistTableProductsForAnExistingItem() throws BusinessException {

		final SaleItem saleItem = new SaleItem();

		final Stock stock = new Stock();
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(150));

		saleItem.setStock(stock);

		saleItem.setQuantity(new BigDecimal(10));
		saleItem.setDeliveredQuantity(new BigDecimal(10));
		saleItem.setSaleItemValue(new BigDecimal(100));
		saleItem.setDiscount(BigDecimal.ZERO);

		this.sale.addItem(saleItem);

		Mockito.when(this.saleItemPort.findBySaleAndProductUuid(this.sale.getUuid(), saleItem.getUuid())).thenReturn(Optional.of(saleItem));
		Mockito.when(this.salePort.fetchByUuid(this.sale.getUuid())).thenReturn(this.sale);

		this.registTableItemsUseCase.registTableItems(this.context, this.sale);

		Mockito.verify(this.saleItemPort, Mockito.times(1)).updateSaleItem(this.context, saleItem);
		Mockito.verify(this.saleItemPort, Mockito.times(1)).findBySaleAndProductUuid(this.sale.getUuid(), saleItem.getStock().get().getUuid());
		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(this.context, this.sale);

		Assert.assertNotEquals(this.sale.getTotal(), BigDecimal.ZERO);
	}

	@Test
	public void shouldRegistNewTableServices() throws BusinessException {

		final SaleItem saleItem = new SaleItem();

		final ServiceItem serviceItem = new ServiceItem();
		serviceItem.setSalePrice(new BigDecimal(130));

		saleItem.setServiceItem(serviceItem);

		saleItem.setQuantity(new BigDecimal(10));
		saleItem.setDeliveredQuantity(new BigDecimal(10));
		saleItem.setSaleItemValue(new BigDecimal(100));
		saleItem.setDiscount(BigDecimal.ZERO);

		this.sale.addItem(saleItem);

		Mockito.when(this.salePort.fetchByUuid(this.sale.getUuid())).thenReturn(this.sale);

		this.registTableItemsUseCase.registTableItems(this.context, this.sale);

		Mockito.verify(this.saleItemPort, Mockito.times(0)).updateSaleItem(this.context, saleItem);
		Mockito.verify(this.saleItemPort, Mockito.times(1)).createSaleItem(this.context, saleItem);
		Mockito.verify(this.saleItemPort, Mockito.times(1)).findBySaleAndServiceUuid(this.sale.getUuid(), saleItem.getServiceItem().get().getUuid());
		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(this.context, this.sale);

		Assert.assertNotEquals(this.sale.getTotal(), BigDecimal.ZERO);
	}

	@Test
	public void shouldRegistExistingTableServices() throws BusinessException {

		final SaleItem saleItem = new SaleItem();

		final ServiceItem serviceItem = new ServiceItem();
		serviceItem.setSalePrice(new BigDecimal(130));

		saleItem.setServiceItem(serviceItem);

		saleItem.setQuantity(new BigDecimal(10));
		saleItem.setDeliveredQuantity(new BigDecimal(10));
		saleItem.setSaleItemValue(new BigDecimal(100));
		saleItem.setDiscount(BigDecimal.ZERO);

		this.sale.addItem(saleItem);

		Mockito.when(this.saleItemPort.findBySaleAndServiceUuid(this.sale.getUuid(),
				saleItem.getUuid())).thenReturn(Optional.of(saleItem));

		Mockito.when(this.salePort.fetchByUuid(this.sale.getUuid())).thenReturn(this.sale);

		this.registTableItemsUseCase.registTableItems(this.context, this.sale);

		Mockito.verify(this.saleItemPort, Mockito.times(1)).updateSaleItem(this.context, saleItem);
		Mockito.verify(this.saleItemPort, Mockito.times(0)).createSaleItem(this.context, saleItem);
		Mockito.verify(this.saleItemPort, Mockito.times(1)).findBySaleAndServiceUuid(this.sale.getUuid(), saleItem.getServiceItem().get().getUuid());
		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(this.context, this.sale);

		Assert.assertNotEquals(this.sale.getTotal(), BigDecimal.ZERO);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotAddItemsTableWithoutItems() throws BusinessException {
		this.registTableItemsUseCase.registTableItems(this.context, new Sale());
	}
}
