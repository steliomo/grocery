/**
 *
 */
package mz.co.grocery.integ.resources.stock;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
import mz.co.grocery.integ.resources.stock.dto.StockDTO;
import mz.co.grocery.integ.resources.stock.dto.StocksDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

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
	public Response createStock(final StockDTO stockDTO) throws BusinessException {
		this.stockService.createStock(this.getContext(), stockDTO.get());
		return Response.ok(stockDTO).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStock(final StockDTO stockDTO) throws BusinessException {
		this.stockService.updateStock(this.getContext(), stockDTO.get());
		return Response.ok(stockDTO).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllStocks(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {
		final Long totalItems = this.stockQueryService.count(EntityStatus.ACTIVE);
		final List<Stock> stocks = this.stockQueryService.fetchAllStocks(currentPage, maxResult);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> new StockDTO(stock)).collect(Collectors.toList());
		final StocksDTO stockDTO = new StocksDTO(stocksDTO, totalItems);

		return Response.ok(stockDTO).build();
	}

	@GET
	@Path("{stockUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchByUuid(@PathParam("stockUuid") final String stockUuid) throws BusinessException {
		final Stock stock = this.stockQueryService.fetchStockByUuid(stockUuid);
		return Response.ok(new StockDTO(stock)).build();
	}

	@GET
	@Path("by-description")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksByProductDescription(@QueryParam("description") final String description)
			throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchStocksByProductDescription(description);
		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> new StockDTO(stock)).collect(Collectors.toList());
		return Response.ok(stocksDTO).build();
	}

	@DELETE
	@Path("{stockUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeStock(@PathParam("stockUuid") final String stockUuid) throws BusinessException {
		final Stock stock = this.stockQueryService.fetchStockByUuid(stockUuid);
		this.stockService.removeStock(this.getContext(), stock);
		return Response.ok(new StockDTO(stock)).build();
	}

	@PUT
	@Path("update-stocks-and-prices")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStockQuantity(final List<StockDTO> stocksDTO) throws BusinessException {

		for (final StockDTO stockDTO : stocksDTO) {
			this.stockService.updateStocksAndPrices(this.getContext(), stockDTO.get());
		}

		return Response.ok().build();
	}

	@GET
	@Path("by-grocery-and-product")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fecthStocksByGroceryAndProduct(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("productUuid") final String productUuid) throws BusinessException {

		final List<Stock> stocks = this.stockQueryService.fetchStockByGroceryAndProduct(groceryUuid, productUuid);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> new StockDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksDTO).build();
	}

	@GET
	@Path("by-grocery/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksByGrocery(@PathParam("groceryUuid") final String groceryUuid) throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchStocksByGrocery(groceryUuid);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> new StockDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksDTO).build();
	}

	@GET
	@Path("by-grocery-and-sale-period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksByGroceryAndSalePeriod(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate)
					throws BusinessException {

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();
		final LocalDate sDate = dateAdapter.unmarshal(startDate);
		final LocalDate eDate = dateAdapter.unmarshal(endDate);

		final List<Stock> stocks = this.stockQueryService.fetchLowStocksByGroceryAndSalePeriod(groceryUuid, sDate,
				eDate);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> new StockDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksDTO).build();
	}

	@GET
	@Path("not-in-this-grocery-by-product")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fecthStocksNotInThisByGroceryByProduct(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("productUuid") final String productUuid) throws BusinessException {

		final List<Stock> stocks = this.stockQueryService.fetchStockNotInthisGroceryByProduct(groceryUuid, productUuid);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> new StockDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksDTO).build();
	}

	@POST
	@Path("add-stock-products")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStockProducts(final List<StockDTO> stocksDTO) throws BusinessException {

		for (final StockDTO stockDTO : stocksDTO) {
			this.stockService.createStock(this.getContext(), stockDTO.get());
		}

		return Response.ok().build();
	}
}
