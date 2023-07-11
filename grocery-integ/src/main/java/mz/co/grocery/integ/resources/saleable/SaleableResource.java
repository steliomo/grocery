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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.sale.in.UpdateServiceItemPriceUseCase;
import mz.co.grocery.core.application.sale.in.UpdateStockAndPricesUseCase;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.saleable.dto.SaleableDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Path("saleables")
@WebAdapter
public class SaleableResource extends AbstractResource {

	@Inject
	private StockPort stockPort;

	@Inject
	private ServiceItemPort serviceItemPort;

	@Autowired
	private DTOMapper<StockDTO, Stock> stockMapper;

	@Autowired
	private DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper;

	@Inject
	private UpdateServiceItemPriceUseCase updateServiceItemPriceUseCase;

	@Inject
	private UpdateStockAndPricesUseCase updateStockAndPricesUseCase;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSaleable(final SaleableDTO saleable) throws BusinessException {

		for (final StockDTO stock : saleable.getStocks()) {
			this.stockPort.createStock(this.getContext(), this.stockMapper.toDomain(stock));
		}

		for (final ServiceItemDTO serviceItem : saleable.getServiceItems()) {
			this.serviceItemPort.createServiceItem(this.getContext(), this.serviceItemMapper.toDomain(serviceItem));
		}

		return Response.ok(saleable).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSaleable(final SaleableDTO saleable) throws BusinessException {

		for (final StockDTO stock : saleable.getStocks()) {
			this.updateStockAndPricesUseCase.updateStocksAndPrices(this.getContext(), this.stockMapper.toDomain(stock));
		}

		for (final ServiceItemDTO serviceItem : saleable.getServiceItems()) {
			this.updateServiceItemPriceUseCase.updateServiceItemPrice(this.getContext(), this.serviceItemMapper.toDomain(serviceItem));
		}

		return Response.ok(saleable).build();
	}
}
