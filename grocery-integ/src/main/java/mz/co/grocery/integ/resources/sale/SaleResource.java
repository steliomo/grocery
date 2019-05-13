/**
 *
 */
package mz.co.grocery.integ.resources.sale;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registSale(final Sale sale) throws BusinessException {
		this.saleService.registSale(this.getContext(), sale);
		return Response.ok(sale).build();
	}
}
