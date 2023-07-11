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
import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ServiceDescriptionTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionPortTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Inject
	private ServicePort servicePort;

	private String serviceDescriptionUuid;

	private String serviceDescriptionName;

	@Before
	public void setup() {
		EntityFactory.gimme(ServiceDescription.class, 10, ServiceDescriptionTemplate.VALID, result -> {
			ServiceDescription serviceDescription = null;

			if (result instanceof ServiceDescription) {
				serviceDescription = (ServiceDescription) result;
				try {
					final Service service = this.servicePort.createService(this.getUserContext(), serviceDescription.getService().get());
					serviceDescription.setService(service);

					serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(), serviceDescription);
					this.serviceDescriptionUuid = serviceDescription.getUuid();
					this.serviceDescriptionName = serviceDescription.getName();
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

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionPort.findAllServiceDescriptions(currentPage, maxResult);

		Assert.assertFalse(serviceDescriptions.isEmpty());
		Assert.assertEquals(serviceDescriptions.size(), maxResult);
	}

	@Test
	public void shouldFetchServiceDescriptionByuuid() throws BusinessException {
		final ServiceDescription serviceDescription = this.serviceDescriptionPort.fetchServiceDescriptionByUuid(this.serviceDescriptionUuid);

		Assert.assertNotNull(serviceDescription.getService());
		Assert.assertEquals(this.serviceDescriptionUuid, serviceDescription.getUuid());
	}

	@Test
	public void shouldFetchServiceDescriptionByName() throws BusinessException {

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionPort
				.fetchServiceDescriptionByName(this.serviceDescriptionName);

		Assert.assertFalse(serviceDescriptions.isEmpty());

		serviceDescriptions.forEach(serviceDescription -> {
			Assert.assertTrue(serviceDescription.getName().contains(this.serviceDescriptionName));
			Assert.assertNotNull(serviceDescription.getService());
		});

	}
}
