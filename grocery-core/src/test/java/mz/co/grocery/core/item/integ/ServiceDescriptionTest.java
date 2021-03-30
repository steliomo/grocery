/**
 *
 */
package mz.co.grocery.core.item.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ServiceDescriptionTemplate;
import mz.co.grocery.core.item.model.ServiceDescription;
import mz.co.grocery.core.item.service.ServiceDescriptionService;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceService serviceService;

	private ServiceDescription serviceDescription;

	@Before
	public void setup() throws BusinessException {
		this.serviceDescription = EntityFactory.gimme(ServiceDescription.class, ServiceDescriptionTemplate.VALID);

		this.serviceService.createService(this.getUserContext(), this.serviceDescription.getService());
	}

	@Test
	public void createServiceDescription() throws BusinessException {

		this.serviceDescriptionService.createServiceDescription(this.getUserContext(), this.serviceDescription);

		TestUtil.assertCreation(this.serviceDescription);
	}

	@Test
	public void updateServiceDescription() throws BusinessException {

		this.serviceDescriptionService.createServiceDescription(this.getUserContext(), this.serviceDescription);
		this.serviceDescriptionService.updateServiceDescription(this.getUserContext(), this.serviceDescription);

		TestUtil.assertUpdate(this.serviceDescription);
	}
}
