/**
 *
 */
package mz.co.grocery.core.item.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.ServiceItemTemplate;
import mz.co.grocery.core.fixturefactory.ServiceTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.model.Service;
import mz.co.grocery.core.item.service.ServiceDescriptionService;
import mz.co.grocery.core.item.service.ServiceQueryService;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.service.ServiceItemService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceQueryService serviceQueryService;

	@Inject
	private ServiceItemService serviceItemService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private GroceryService groceryService;

	@Before
	public void setup() {

		EntityFactory.gimme(Service.class, 10, ServiceTemplate.VALID, service -> {
			try {
				this.serviceService.createService(this.getUserContext(), (Service) service);
			} catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFindAllServices() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 8;

		final List<Service> services = this.serviceQueryService.findAllServices(currentPage, maxResult);

		Assert.assertFalse(services.isEmpty());
		Assert.assertEquals(services.size(), maxResult);
	}

	@Test
	public void shouldFindServiceByName() throws BusinessException {

		final List<Service> services = this.serviceQueryService.findServicesByName("Corte de");
		Assert.assertFalse(services.isEmpty());
	}

	@Test
	public void shouldFindServicesByUnit() throws BusinessException {

		final Grocery unit = this.groceryService.createGrocery(this.getUserContext(), EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {

				final ServiceItem serviceItem = (ServiceItem) result;

				try {
					this.serviceService.createService(this.getUserContext(), serviceItem.getServiceDescription().getService());
					this.serviceDescriptionService.createServiceDescription(this.getUserContext(), serviceItem.getServiceDescription());
					serviceItem.setUnit(unit);
					this.serviceItemService.createServiceItem(this.getUserContext(), serviceItem);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		final List<Service> services = this.serviceQueryService.findServicesByUnit(unit);

		Assert.assertFalse(services.isEmpty());
		Assert.assertEquals(services.size(), 10);
	}

	@Test
	public void shouldFindServicesNotInThisUnit() throws BusinessException {

		final Grocery unit = this.groceryService.createGrocery(this.getUserContext(), EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {

				final ServiceItem serviceItem = (ServiceItem) result;

				try {
					this.serviceService.createService(this.getUserContext(), serviceItem.getServiceDescription().getService());
					this.serviceDescriptionService.createServiceDescription(this.getUserContext(), serviceItem.getServiceDescription());
					this.groceryService.createGrocery(this.getUserContext(), serviceItem.getUnit());
					this.serviceItemService.createServiceItem(this.getUserContext(), serviceItem);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		final List<Service> services = this.serviceQueryService.findServicesNotInthisUnit(unit);

		Assert.assertFalse(services.isEmpty());
	}
}
