/**
 *
 */
package mz.co.grocery.core.product.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ServiceItemTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.product.model.ServiceItem;
import mz.co.grocery.core.product.service.ServiceDescriptionService;
import mz.co.grocery.core.product.service.ServiceItemService;
import mz.co.grocery.core.product.service.ServiceService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ServiceItemService serviceItemService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceService serviceService;

	private ServiceItem serviceItem;

	@Before
	public void setup() throws BusinessException {
		this.serviceItem = EntityFactory.gimme(ServiceItem.class, ServiceItemTemplate.VALID);

		this.groceryService.createGrocery(this.getUserContext(), this.serviceItem.getUnit());
		this.serviceService.createService(this.getUserContext(), this.serviceItem.getServiceDescription().getService());
		this.serviceDescriptionService.createServiceDescription(this.getUserContext(), this.serviceItem.getServiceDescription());
	}

	@Test
	public void createServiceItem() throws BusinessException {

		this.serviceItemService.createServiceItem(this.getUserContext(), this.serviceItem);

		TestUtil.assertCreation(this.serviceItem);
	}
}
