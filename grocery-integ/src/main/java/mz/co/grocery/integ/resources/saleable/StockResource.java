/**
 *
 */
package mz.co.grocery.integ.resources.saleable;

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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.grocery.integ.resources.saleable.dto.StocksDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */

@Path("stocks")
@WebAdapter
public class StockResource extends AbstractResource {

	@Inject
	private StockPort stockPort;

	@Autowired
	private DTOMapper<StockDTO, Stock> stockMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStock(final StockDTO stockDTO) throws BusinessException {
		Stock stock = this.stockMapper.toDomain(stockDTO);

		stock = this.stockPort.createStock(this.getContext(), stock);

		return Response.ok(this.stockMapper.toDTO(stock)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStock(final StockDTO stockDTO) throws BusinessException {

		Stock stock = this.stockMapper.toDomain(stockDTO);

		stock = this.stockPort.updateStock(this.getContext(), stock);

		return Response.ok(this.stockMapper.toDTO(stock)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllStocks(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {
		final Long totalItems = this.stockPort.count(EntityStatus.ACTIVE);

		final List<Stock> stocks = this.stockPort.fetchAllStocks(currentPage, maxResult);

		return Response.ok(new StocksDTO(stocks, totalItems, this.stockMapper)).build();
	}

	@GET
	@Path("{stockUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchByUuid(@PathParam("stockUuid") final String stockUuid) throws BusinessException {

		final Stock stock = this.stockPort.fetchStockByUuid(stockUuid);

		return Response.ok(this.stockMapper.toDTO(stock)).build();
	}

	@GET
	@Path("by-description")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksByProductDescription(@QueryParam("description") final String description)
			throws BusinessException {
		final List<Stock> stocks = this.stockPort.fetchStocksByProductDescription(description);

		return Response.ok(new StocksDTO(stocks, 0L, this.stockMapper).getStocksDTO()).build();
	}

	@DELETE
	@Path("{stockUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeStock(@PathParam("stockUuid") final String stockUuid) throws BusinessException {
		Stock stock = this.stockPort.fetchStockByUuid(stockUuid);

		stock = this.stockPort.removeStock(this.getContext(), stock);

		return Response.ok(this.stockMapper.toDTO(stock)).build();
	}

	@GET
	@Path("by-grocery-and-product")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fecthStocksByGroceryAndProduct(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("productUuid") final String productUuid) throws BusinessException {

		final List<Stock> stocks = this.stockPort.fetchStockByGroceryAndProduct(groceryUuid, productUuid);

		return Response.ok(new StocksDTO(stocks, 0L, this.stockMapper).getStocksDTO()).build();
	}

	@GET
	@Path("by-grocery/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksByGrocery(@PathParam("groceryUuid") final String groceryUuid) throws BusinessException {
		final List<Stock> stocks = this.stockPort.fetchStocksByGrocery(groceryUuid);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> this.stockMapper.toDTO(stock)).collect(Collectors.toList());

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

		final List<Stock> stocks = this.stockPort.fetchLowStocksByGroceryAndSalePeriod(groceryUuid, sDate,
				eDate);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> this.stockMapper.toDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksDTO).build();
	}

	@GET
	@Path("not-in-this-grocery-by-product")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fecthStocksNotInThisByGroceryByProduct(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("productUuid") final String productUuid) throws BusinessException {

		final List<Stock> stocks = this.stockPort.fetchStockNotInthisGroceryByProduct(groceryUuid, productUuid);

		final List<StockDTO> stocksDTO = stocks.stream().map(stock -> this.stockMapper.toDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksDTO).build();
	}

	@GET
	@Path("in-analysis")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchStocksInAnalysis(@QueryParam("unitUuid") final String unitUuid) throws BusinessException {

		final List<Stock> stocksInAnalysis = this.stockPort.fetchStocksInAnalysisByUnit(unitUuid);
		final List<StockDTO> stocksAnalysisDTOs = stocksInAnalysis.stream().map(stock -> this.stockMapper.toDTO(stock)).collect(Collectors.toList());

		return Response.ok(stocksAnalysisDTOs).build();
	}

	@POST
	@Path("regularize")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response regulizeStock(final StockDTO stockDTO) throws BusinessException {

		Stock stock = this.stockMapper.toDomain(stockDTO);

		stock = this.stockPort.regularize(this.getContext(), stock);

		return Response.ok(this.stockMapper.toDTO(stock)).build();
	}
}
