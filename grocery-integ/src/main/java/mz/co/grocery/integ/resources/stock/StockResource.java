/**
 *
 */
package mz.co.grocery.integ.resources.stock;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

	@GET
	@Path("{stockUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchByUuid(@PathParam("stockUuid") final String stockUuid) throws BusinessException {
		final Stock stock = this.stockQueryService.fetchStockByUuid(stockUuid);
		return Response.ok(stock).build();
	}

	@GET
	@Path("by-description")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksByProductDescription(@QueryParam("description") final String description)
	        throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchStocksByProductDescription(description);
		return Response.ok(stocks).build();
	}

	@DELETE
	@Path("{stockUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeStock(@PathParam("stockUuid") final String stockUuid) throws BusinessException {
		final Stock stock = this.stockQueryService.fetchStockByUuid(stockUuid);
		this.stockService.removeStock(this.getContext(), stock);
		return Response.ok(stock).build();
	}

	@PUT
	@Path("add-quantity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStockQuantity(final StockDTO stockDTO) throws BusinessException {
		final List<StockQuantity> stockQuantities = stockDTO.getStockQuantities();

		stockQuantities.forEach(stockQuantity -> {
			this.addStockQuantity(stockQuantity);
		});

		return Response.ok().build();
	}

	private void addStockQuantity(final StockQuantity stockQuantity) {
		try {
			final Stock stock = this.stockQueryService.findStockByUuid(stockQuantity.getStock().getUuid());
			this.stockService.addStockQuantity(this.getContext(), stock, stockQuantity.getQuantity());
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Path("by-product/{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fecthByProductUuid(@PathParam("productUuid") final String productUuid) throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchStockByProductUuid(productUuid);
		return Response.ok(stocks).build();
	}
}
