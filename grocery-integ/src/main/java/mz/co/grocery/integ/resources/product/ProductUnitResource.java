/**
 *
 */
package mz.co.grocery.integ.resources.product;

import java.util.Arrays;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.grocery.core.product.model.ProductUnitType;
import mz.co.grocery.core.product.service.ProductUnitQueryService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("product-units")
@Service(ProductUnitResource.NAME)
public class ProductUnitResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.product.ProductUnitResource";

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductUnitQueryService productUnitQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProductUnit(final ProductUnit productUnit) throws BusinessException {
		this.productUnitService.createProductUnit(this.getContext(), productUnit);
		return Response.ok(productUnit).build();
	}

	@Path("types")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductUnitTypes() {
		final List<ProductUnitType> productUnitTypes = Arrays.asList(ProductUnitType.values());
		return Response.ok(productUnitTypes).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProductUnits() throws BusinessException {
		final List<ProductUnit> productUnits = this.productUnitQueryService.findAllProductUnits();
		return Response.ok(productUnits).build();
	}

	@Path("{productUnitUuid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductUnitByUuid(@PathParam("productUnitUuid") final String productUnitUuid)
	        throws BusinessException {
		final ProductUnit productUnit = this.productUnitQueryService.findProductUnitByUuid(productUnitUuid);
		return Response.ok(productUnit).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProductUnit(final ProductUnit productUnit) throws BusinessException {
		this.productUnitService.updateProductUnit(this.getContext(), productUnit);
		return Response.ok(productUnit).build();
	}

	@Path("{productUnitUuid}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductUnit(@PathParam("productUnitUuid") final String productUnitUuid)
	        throws BusinessException {
		final ProductUnit productUnit = this.productUnitQueryService.findProductUnitByUuid(productUnitUuid);
		this.productUnitService.removeProductUnit(this.getContext(), productUnit);
		return Response.ok(productUnit).build();
	}
}