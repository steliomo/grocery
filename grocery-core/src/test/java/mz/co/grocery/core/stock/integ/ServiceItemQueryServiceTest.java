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
import mz.co.grocery.core.fixturefactory.ServiceItemTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
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

	@Before
	public void setup() throws BusinessException {
		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {
				final ServiceItem serviceItem = (ServiceItem) result;

				try {
					this.groceryService.createGrocery(this.getUserContext(), serviceItem.getUnit());
					this.serviceService.createService(this.getUserContext(), serviceItem.getServiceDescription().getService());
					this.serviceDescriptionService.createServiceDescription(this.getUserContext(), serviceItem.getServiceDescription());
					this.serviceItemService.createServiceItem(this.getUserContext(), serviceItem);
					this.serviceItemUuid = serviceItem.getUuid();
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
	public void shoulServiceItemByUuid() throws BusinessException {

		final ServiceItem serviceItem = this.serviceItemQueryService.fetchServiceItemByUuid(this.serviceItemUuid);

		Assert.assertEquals(this.serviceItemUuid, serviceItem.getUuid());

		Assert.assertNotNull(serviceItem.getServiceDescription());
		Assert.assertNotNull(serviceItem.getServiceDescription().getService());
		Assert.assertNotNull(serviceItem.getUnit());
	}
}
