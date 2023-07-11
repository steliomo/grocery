/**
 *
 */
package mz.co.grocery.persistence.item;

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
import mz.co.grocery.persistence.fixturefactory.ServiceTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServicePortTest extends AbstractIntegServiceTest {

	@Inject
	private ServicePort servicePort;

	@Inject
	private ServiceItemPort serviceItemPort;

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Inject
	private UnitPort unitPort;

	@Before
	public void setup() {

		EntityFactory.gimme(Service.class, 10, ServiceTemplate.VALID, service -> {
			try {
				this.servicePort.createService(this.getUserContext(), (Service) service);
			} catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFindAllServices() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 8;

		final List<Service> services = this.servicePort.findAllServices(currentPage, maxResult);

		Assert.assertFalse(services.isEmpty());
		Assert.assertEquals(services.size(), maxResult);
	}

	@Test
	public void shouldFindServiceByName() throws BusinessException {

		final List<Service> services = this.servicePort.findServicesByName("Corte de");
		Assert.assertFalse(services.isEmpty());
	}

	@Test
	public void shouldFindServicesByUnit() throws BusinessException {

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {

				ServiceItem serviceItem = (ServiceItem) result;

				try {
					ServiceDescription serviceDescription = serviceItem.getServiceDescription().get();

					final Service service = this.servicePort.createService(this.getUserContext(), serviceDescription.getService().get());
					serviceDescription.setService(service);

					serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(), serviceDescription);
					serviceItem.setUnit(unit);
					serviceItem.setServiceDescription(serviceDescription);

					serviceItem = this.serviceItemPort.createServiceItem(this.getUserContext(), serviceItem);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		final List<Service> services = this.servicePort.findServicesByUnit(unit);

		Assert.assertFalse(services.isEmpty());
		Assert.assertEquals(services.size(), 10);
	}

	@Test
	public void shouldFindServicesNotInThisUnit() throws BusinessException {

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		EntityFactory.gimme(ServiceItem.class, 10, ServiceItemTemplate.VALID, result -> {
			if (result instanceof ServiceItem) {

				ServiceItem serviceItem = (ServiceItem) result;

				try {
					ServiceDescription serviceDescription = serviceItem.getServiceDescription().get();

					final Service service = this.servicePort.createService(this.getUserContext(), serviceDescription.getService().get());
					serviceDescription.setService(service);

					serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(), serviceDescription);

					final Unit unitD = this.unitPort.createUnit(this.getUserContext(), serviceItem.getUnit().get());

					serviceItem.setUnit(unitD);
					serviceItem.setServiceDescription(serviceDescription);

					serviceItem = this.serviceItemPort.createServiceItem(this.getUserContext(), serviceItem);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		final List<Service> services = this.servicePort.findServicesNotInthisUnit(unit);

		Assert.assertFalse(services.isEmpty());
	}
}
