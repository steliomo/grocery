/**
 *
 */
package mz.co.grocery.integ.resources.sale;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.core.sale.service.SaleQueryService;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.sale.dto.SaleDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("sales")
@Service(SaleResource.NAME)
public class SaleResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.sale.SaleResource";

	@Inject
	private SaleService saleService;

	@Inject
	private SaleQueryService saleQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registSale(final SaleDTO saleDTO) throws BusinessException {
		final Sale sale = this.saleService.registSale(this.getContext(), saleDTO.get());
		return Response.ok(new SaleDTO(sale)).build();
	}

	@GET
	@Path("last-7-days/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findLast7DaysSales(@PathParam("groceryUuid") final String groceryUuid) throws BusinessException {
		final List<SaleReport> sales = this.saleQueryService.findLast7DaysSale(groceryUuid);
		return Response.ok(sales).build();
	}

	@GET
	@Path("per-period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSalesPerPeriod(@QueryParam("groceryUuid") final String groceryUuid,
	        @QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate)
	        throws BusinessException {

		final LocalDateAdapter dateAdapter = new LocalDateAdapter();

		final List<SaleReport> sales = this.saleQueryService.findSalesPerPeriod(groceryUuid,
		        dateAdapter.unmarshal(startDate), dateAdapter.unmarshal(endDate));

		return Response.ok(sales).build();
	}
}
