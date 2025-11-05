/**
 *
 */
package mz.co.grocery.persistence.sale;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.item.out.ServiceDescriptionPort;
import mz.co.grocery.core.application.item.out.ServicePort;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.SaleItemTemplate;
import mz.co.grocery.persistence.fixturefactory.SaleTemplate;
import mz.co.grocery.persistence.saleable.StockBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */

public class SaleItemPortTest extends AbstractIntegServiceTest {

	@Inject
	private SaleItemPort saleItemPort;

	@Inject
	private SalePort salePort;

	@Inject
	private UnitPort unitPort;

	@Inject
	private StockBuilder stockBuilder;

	@Inject
	private ServicePort servicePort;

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Inject
	private ServiceItemPort serviceItemPort;

	@Inject
	private SaleBuilder saleBuilder;

	private Sale sale;

	private Unit unit;

	@Before
	public void setup() throws BusinessException {
		this.sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);
		this.unit = this.unitPort.createUnit(this.getUserContext(), this.sale.getUnit().get());
		this.sale.calculateBilling();
		this.sale.calculateTotal();
		this.sale.updateDeliveryStatus();
		this.sale.setUnit(this.unit);

		this.sale = this.salePort.createSale(this.getUserContext(), this.sale);
	}

	@Test
	public void shouldfindSaleItemBySaleAndProductUuid() throws BusinessException {

		SaleItem item = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.PRODUCT);
		item.setSale(this.sale);
		item.setDeliveredQuantity(item.getQuantity());
		final List<Stock> stocks = this.stockBuilder.quantity(1).unit(this.unit).valid().build();
		item.setDeliveryStatus();
		item.setStock(stocks.get(0));

		item = this.saleItemPort.createSaleItem(this.getUserContext(), item);

		final Optional<SaleItem> saleItemFound = this.saleItemPort.findBySaleAndProductUuid(this.sale.getUuid(), item.getStock().get().getUuid());

		Assert.assertTrue(saleItemFound.isPresent());
	}

	@Test
	public void shouldFindSaleItemBySaleAndProductUuid() throws BusinessException {
		SaleItem item = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.SERVICE);
		item.setSale(this.sale);
		item.setDeliveredQuantity(item.getQuantity());
		item.setDeliveryStatus();

		ServiceItem serviceItem = item.getServiceItem().get();
		ServiceDescription serviceDescription = serviceItem.getServiceDescription().get();

		final Service service = this.servicePort.createService(this.getUserContext(), serviceDescription.getService().get());

		serviceDescription.setService(service);
		serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(), serviceDescription);

		serviceItem.setUnit(this.unit);
		serviceItem.setServiceDescription(serviceDescription);

		serviceItem = this.serviceItemPort.createServiceItem(this.getUserContext(), serviceItem);

		item.setServiceItem(serviceItem);
		item = this.saleItemPort.createSaleItem(this.getUserContext(), item);

		final Optional<SaleItem> foundSaleItem = this.saleItemPort.findBySaleAndServiceUuid(this.sale.getUuid(),
				item.getServiceItem().get().getUuid());

		Assert.assertTrue(foundSaleItem.isPresent());
	}

	@Test
	public void shouldFindDeptItemsByCustomer() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.CREDIT).build();

		final List<DebtItem> deptItems = this.saleItemPort.findDeptItemsByCustomer(sale.getCustomer().get().getUuid());

		Assert.assertFalse(deptItems.isEmpty());
		Assert.assertEquals(5, deptItems.size());
	}
}
