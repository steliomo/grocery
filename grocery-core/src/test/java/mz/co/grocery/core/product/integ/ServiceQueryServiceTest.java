/**
 *
 */
package mz.co.grocery.core.product.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ServiceTemplate;
import mz.co.grocery.core.product.model.Service;
import mz.co.grocery.core.product.service.ServiceQueryService;
import mz.co.grocery.core.product.service.ServiceService;
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
}
