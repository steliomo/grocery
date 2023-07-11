/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.sale.in.UpdateServiceItemPriceUseCase;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.application.sale.service.UpdateServiceItemPriceService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.fixturefactory.ServiceItemTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ServiceItemPort serviceItemPort;

	@InjectMocks
	private final UpdateServiceItemPriceUseCase updateServiceItemPriceUseCase = new UpdateServiceItemPriceService(this.serviceItemPort);

	@Test
	public void shouldUpdateServiceItemPrice() throws BusinessException {

		final ServiceItem serviceItem = EntityFactory.gimme(ServiceItem.class, ServiceItemTemplate.VALID);

		final BigDecimal newPrice = new BigDecimal(500);

		serviceItem.setSalePrice(newPrice);
		Mockito.when(this.serviceItemPort.findsaleItemById(serviceItem.getId())).thenReturn(serviceItem);

		final ServiceItem updatedServiceItem = this.updateServiceItemPriceUseCase.updateServiceItemPrice(this.getUserContext(), serviceItem);

		Assert.assertEquals(newPrice, updatedServiceItem.getSalePrice());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateServiceItemPriceToZero() throws BusinessException {

		final ServiceItem serviceItem = EntityFactory.gimme(ServiceItem.class, ServiceItemTemplate.VALID);
		serviceItem.setSalePrice(BigDecimal.ZERO);

		this.updateServiceItemPriceUseCase.updateServiceItemPrice(this.getUserContext(), serviceItem);
	}

}
