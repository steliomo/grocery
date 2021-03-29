/**
 *
 */
package mz.co.grocery.core.stock.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.ServiceItemTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.product.model.Service;
import mz.co.grocery.core.product.service.ServiceDescriptionService;
import mz.co.grocery.core.product.service.ServiceService;
import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.grocery.core.stock.service.ServiceItemQueryService;
import mz.co.grocery.core.stock.service.ServiceItemService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceItemService serviceItemService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceItemQueryService serviceItemQueryService;

	private String serviceItemUuid;

	private String serviceItemName;

	private Service service;

	private Grocery unit;

	@Before
	public void setup() throws BusinessException {
		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {
				final ServiceItem serviceItem = (ServiceItem) result;

				try {
					this.unit = this.groceryService.createGrocery(this.getUserContext(), serviceItem.getUnit());
					this.serviceService.createService(this.getUserContext(), serviceItem.getServiceDescription().getService());
					this.serviceDescriptionService.createServiceDescription(this.getUserContext(), serviceItem.getServiceDescription());
					this.serviceItemService.createServiceItem(this.getUserContext(), serviceItem);
					this.serviceItemUuid = serviceItem.getUuid();
					this.serviceItemName = serviceItem.getServiceDescription().getName();
					this.service = serviceItem.getServiceDescription().getService();
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Test
	public void shoulFetchAllServiceItems() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 8;

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchAllServiceItems(currentPage, maxResult);

		Assert.assertFalse(serviceItems.isEmpty());
		Assert.assertEquals(maxResult, serviceItems.size());

		serviceItems.forEach(serviceItem -> {
			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().getService());
			Assert.assertNotNull(serviceItem.getUnit());
		});
	}

	@Test
	public void shoulFetchServiceItemByUuid() throws BusinessException {

		final ServiceItem serviceItem = this.serviceItemQueryService.fetchServiceItemByUuid(this.serviceItemUuid);

		Assert.assertEquals(this.serviceItemUuid, serviceItem.getUuid());

		Assert.assertNotNull(serviceItem.getServiceDescription());
		Assert.assertNotNull(serviceItem.getServiceDescription().getService());
		Assert.assertNotNull(serviceItem.getUnit());
	}

	@Test
	public void shoulFetchServiceItemByName() throws BusinessException {

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchServiceItemByName(this.serviceItemName);

		Assert.assertFalse(serviceItems.isEmpty());

		serviceItems.forEach(serviceItem -> {
			Assert.assertTrue(serviceItem.getServiceDescription().getName().contains(this.serviceItemName));
			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().getService());
			Assert.assertNotNull(serviceItem.getUnit());

		});
	}

	@Test
	public void shouldFecthServiceItemByServiceAndUnit() throws BusinessException {
		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchServiceItemsByServiceAndUnit(this.service, this.unit);

		Assert.assertFalse(serviceItems.isEmpty());

		serviceItems.forEach(serviceItem -> {

			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().getService());
			Assert.assertNotNull(serviceItem.getUnit());

			Assert.assertEquals(serviceItem.getServiceDescription().getService().getUuid(), this.service.getUuid());
			Assert.assertEquals(serviceItem.getUnit().getUuid(), this.unit.getUuid());

		});
	}

	@Test
	public void shouldFetchServicItemsNotInThisUnitByService() throws BusinessException {

		final Grocery unit = this.groceryService.createGrocery(this.getUserContext(), EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchServiceItemsNotInThisUnitByService(this.service, unit);

		Assert.assertFalse(serviceItems.isEmpty());

		serviceItems.forEach(serviceItem -> {

			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().getService());

			Assert.assertEquals(serviceItem.getServiceDescription().getService().getUuid(), this.service.getUuid());
		});
	}
}
