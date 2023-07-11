/**
 *
 */
package mz.co.grocery.persistence.saleable;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.item.out.ServiceDescriptionPort;
import mz.co.grocery.core.application.item.out.ServicePort;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ServiceItemTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemPortTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceItemPort serviceItemPort;

	@Inject
	private UnitPort unitPort;

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Inject
	private ServicePort servicePort;

	private String serviceItemUuid;

	private String serviceItemName;

	private Service service;

	private Unit unit;

	@Before
	public void setup() throws BusinessException {
		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {
				ServiceItem serviceItem = (ServiceItem) result;

				try {
					this.unit = this.unitPort.createUnit(this.getUserContext(), serviceItem.getUnit().get());

					ServiceDescription serviceDescription = serviceItem.getServiceDescription().get();

					this.service = this.servicePort.createService(this.getUserContext(), serviceDescription.getService().get());

					serviceDescription.setService(this.service);

					serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(), serviceDescription);

					serviceItem.setUnit(this.unit);
					serviceItem.setServiceDescription(serviceDescription);

					serviceItem = this.serviceItemPort.createServiceItem(this.getUserContext(), serviceItem);

					this.serviceItemUuid = serviceItem.getUuid();
					this.serviceItemName = serviceDescription.getName();
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

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchAllServiceItems(currentPage, maxResult);

		Assert.assertFalse(serviceItems.isEmpty());
		Assert.assertEquals(maxResult, serviceItems.size());

		serviceItems.forEach(serviceItem -> {
			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().get().getService());
			Assert.assertNotNull(serviceItem.getUnit());
		});
	}

	@Test
	public void shoulFetchServiceItemByUuid() throws BusinessException {

		final ServiceItem serviceItem = this.serviceItemPort.fetchServiceItemByUuid(this.serviceItemUuid);

		Assert.assertEquals(this.serviceItemUuid, serviceItem.getUuid());

		Assert.assertNotNull(serviceItem.getServiceDescription());
		Assert.assertNotNull(serviceItem.getServiceDescription().get().getService());
		Assert.assertNotNull(serviceItem.getUnit());
	}

	@Test
	public void shoulFetchServiceItemByName() throws BusinessException {

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchServiceItemByName(this.serviceItemName);

		Assert.assertFalse(serviceItems.isEmpty());

		serviceItems.forEach(serviceItem -> {
			Assert.assertTrue(serviceItem.getServiceDescription().get().getName().contains(this.serviceItemName));
			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().get().getService());
			Assert.assertNotNull(serviceItem.getUnit());

		});
	}

	@Test
	public void shouldFecthServiceItemByServiceAndUnit() throws BusinessException {
		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchServiceItemsByServiceAndUnit(this.service, this.unit);

		Assert.assertFalse(serviceItems.isEmpty());

		serviceItems.forEach(serviceItem -> {

			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().get().getService());
			Assert.assertNotNull(serviceItem.getUnit());

			Assert.assertEquals(serviceItem.getServiceDescription().get().getService().get().getUuid(), this.service.getUuid());
			Assert.assertEquals(serviceItem.getUnit().get().getUuid(), this.unit.getUuid());

		});
	}

	@Test
	public void shouldFetchServicItemsNotInThisUnitByService() throws BusinessException {

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchServiceItemsNotInThisUnitByService(this.service, unit);

		Assert.assertFalse(serviceItems.isEmpty());

		serviceItems.forEach(serviceItem -> {

			Assert.assertNotNull(serviceItem.getServiceDescription());
			Assert.assertNotNull(serviceItem.getServiceDescription().get().getService());

			Assert.assertEquals(serviceItem.getServiceDescription().get().getService().get().getUuid(), this.service.getUuid());
		});
	}
}
