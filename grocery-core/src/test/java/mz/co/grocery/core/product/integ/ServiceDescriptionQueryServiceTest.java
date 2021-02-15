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
import mz.co.grocery.core.fixturefactory.ServiceDescriptionTemplate;
import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.grocery.core.product.service.ServiceDescriptionQueryService;
import mz.co.grocery.core.product.service.ServiceDescriptionService;
import mz.co.grocery.core.product.service.ServiceService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceDescriptionQueryService serviceDescriptionQueryService;

	private String serviceDescriptionUuid;

	@Before
	public void setup() {
		EntityFactory.gimme(ServiceDescription.class, 10, ServiceDescriptionTemplate.VALID, result -> {
			ServiceDescription serviceDescription = null;

			if (result instanceof ServiceDescription) {
				serviceDescription = (ServiceDescription) result;
				try {
					this.serviceService.createService(this.getUserContext(), serviceDescription.getService());
					this.serviceDescriptionService.createServiceDescription(this.getUserContext(), serviceDescription);
					this.serviceDescriptionUuid = serviceDescription.getUuid();
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Test
	public void shouldFindAllServiceDescriptions() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 8;

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionQueryService.findAllServiceDescriptions(currentPage, maxResult);

		Assert.assertFalse(serviceDescriptions.isEmpty());
		Assert.assertEquals(serviceDescriptions.size(), maxResult);
	}

	@Test
	public void shouldFetchServiceDescriptionByuuid() throws BusinessException {
		final ServiceDescription serviceDescription = this.serviceDescriptionQueryService.fetchServiceDescriptionByUuid(this.serviceDescriptionUuid);

		Assert.assertNotNull(serviceDescription.getService());
		Assert.assertEquals(this.serviceDescriptionUuid, serviceDescription.getUuid());
	}
}
