/**
 *
 */
package mz.co.grocery.integ.resources.stock;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.service.StockQueryService;
import mz.co.grocery.core.stock.service.StockService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */

@Path("stocks")
@Service(StockResource.NAME)
public class StockResource extends AbstractResource {
	public static final String NAME = "mz.co.grocery.integ.resources.stock.StockResource";

	@Inject
	private StockService stockService;

	@Inject
	private StockQueryService stockQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStock(final Stock stock) throws BusinessException {
		this.stockService.createStock(this.getContext(), stock);
		return Response.ok(stock).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStock(final Stock stock) throws BusinessException {
		this.stockService.updateStock(this.getContext(), stock);
		return Response.ok(stock).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllStocks(@QueryParam("currentPage") final int currentPage,
	        @QueryParam("maxResult") final int maxResult) throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchAllStocks(currentPage, maxResult);
		final Long totalItems = this.stockQueryService.count(EntityStatus.ACTIVE);

		final StockDTO stockDTO = new StockDTO(stocks, totalItems);

		return Response.ok(stockDTO).build();
	}
}
