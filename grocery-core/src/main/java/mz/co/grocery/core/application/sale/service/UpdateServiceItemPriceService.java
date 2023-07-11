/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.math.BigDecimal;

import mz.co.grocery.core.application.sale.in.UpdateServiceItemPriceUseCase;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class UpdateServiceItemPriceService extends AbstractService implements UpdateServiceItemPriceUseCase {

	private ServiceItemPort serviceItemPort;

	public UpdateServiceItemPriceService(final ServiceItemPort serviceItemPort) {
		this.serviceItemPort = serviceItemPort;
	}

	@Override
	public ServiceItem updateServiceItemPrice(final UserContext userContext, final ServiceItem serviceItem) throws BusinessException {

		if (BigDecimal.ZERO.doubleValue() == serviceItem.getSalePrice().doubleValue()) {
			throw new BusinessException("the.service.item.price.cannot.be.zero");
		}

		final ServiceItem foundServiceItem = this.serviceItemPort.findsaleItemById(serviceItem.getId());
		foundServiceItem.setSalePrice(serviceItem.getSalePrice());

		this.serviceItemPort.updateServiceItem(userContext, foundServiceItem);

		return foundServiceItem;
	}
}
