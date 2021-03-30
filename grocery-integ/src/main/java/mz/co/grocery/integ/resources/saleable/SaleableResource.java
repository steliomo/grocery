/**
 *
 */
package mz.co.grocery.integ.resources.saleable;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.saleable.service.ServiceItemService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.saleable.dto.SaleableDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@Path("saleables")
@Service(SaleableResource.NAME)
public class SaleableResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.saleable.SaleableResource";

	@Inject
	private StockService stockService;

	@Inject
	private ServiceItemService serviceItemService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSaleable(final SaleableDTO saleable) throws BusinessException {

		for (final StockDTO stock : saleable.getStocks()) {
			this.stockService.createStock(this.getContext(), stock.get());
		}

		for (final ServiceItemDTO serviceItem : saleable.getServiceItems()) {
			this.serviceItemService.createServiceItem(this.getContext(), serviceItem.get());
		}

		return Response.ok(saleable).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSaleable(final SaleableDTO saleable) throws BusinessException {

		for (final StockDTO stock : saleable.getStocks()) {
			this.stockService.updateStocksAndPrices(this.getContext(), stock.get());
		}

		for (final ServiceItemDTO serviceItem : saleable.getServiceItems()) {
			this.serviceItemService.updateServiceItemPrice(this.getContext(), serviceItem.get());
		}

		return Response.ok(saleable).build();
	}
}
