/**
 *
 */
package mz.co.grocery.core.stock.unit;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.ServiceItemTemplate;
import mz.co.grocery.core.stock.dao.ServiceItemDAO;
import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.grocery.core.stock.service.ServiceItemService;
import mz.co.grocery.core.stock.service.ServiceItemServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final ServiceItemService serviceItemService = new ServiceItemServiceImpl();

	@Mock
	private ServiceItemDAO serviceItemDAO;

	@Test
	public void shouldUpdateServiceItemPrice() throws BusinessException {

		final ServiceItem serviceItem = EntityFactory.gimme(ServiceItem.class, ServiceItemTemplate.VALID);

		final BigDecimal newPrice = new BigDecimal(500);

		serviceItem.setSalePrice(newPrice);
		Mockito.when(this.serviceItemDAO.findById(serviceItem.getId())).thenReturn(serviceItem);

		final ServiceItem updatedServiceItem = this.serviceItemService.updateServiceItemPrice(this.getUserContext(), serviceItem);

		Assert.assertEquals(newPrice, updatedServiceItem.getSalePrice());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateServiceItemPriceToZero() throws BusinessException {

		final ServiceItem serviceItem = EntityFactory.gimme(ServiceItem.class, ServiceItemTemplate.VALID);
		serviceItem.setSalePrice(BigDecimal.ZERO);

		this.serviceItemService.updateServiceItemPrice(this.getUserContext(), serviceItem);
	}

}
