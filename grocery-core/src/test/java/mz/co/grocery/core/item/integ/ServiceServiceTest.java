/**
 *
 */
package mz.co.grocery.core.item.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ServiceTemplate;
import mz.co.grocery.core.item.model.Service;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceService serviceService;

	private Service service;

	@Before
	public void setup() {
		this.service = EntityFactory.gimme(Service.class, ServiceTemplate.VALID);
	}

	@Test
	public void shouldCreateService() throws BusinessException {
		this.serviceService.createService(this.getUserContext(), this.service);
		TestUtil.assertCreation(this.service);
	}

	@Test
	public void shouldUpdateService() throws BusinessException {
		this.serviceService.createService(this.getUserContext(), this.service);
		this.serviceService.updateService(this.getUserContext(), this.service);
		TestUtil.assertUpdate(this.service);
	}
}
