/**
 *
 */
package mz.co.grocery.integ.resources.product;

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

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.product.service.ProductDescriptionQueryService;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.product.dto.ProductDescriptionsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("product-descriptions")
@Service(ProductDescriptionResource.NAME)
public class ProductDescriptionResource extends AbstractResource {

	public static final String NAME = " mz.co.grocery.integ.resources.product.ProductDescriptionResource";

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private ProductDescriptionQueryService productDescriptionQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProductDescription(final ProductDescription productDescription) throws BusinessException {
		this.productDescriptionService.createProductDescription(this.getContext(), productDescription);
		return Response.ok(productDescription).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProductDescriptions(@QueryParam("currentPage") final int currentPage,
	        @QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<ProductDescription> productDescriptions = this.productDescriptionQueryService
		        .fetchdAllProductDescriptions(currentPage, maxResult);
		final Long totalItems = this.productDescriptionQueryService.countProductDescriptions();

		final ProductDescriptionsDTO productDescriptionDTO = new ProductDescriptionsDTO(productDescriptions, totalItems);
		return Response.ok(productDescriptionDTO).build();
	}

	@Path("by-description")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductDescriptionByDescription(@QueryParam("description") final String description)
	        throws BusinessException {

		final List<ProductDescription> productDescriptions = this.productDescriptionQueryService
		        .fetchProductDescriptionByDescription(description);

		return Response.ok(productDescriptions).build();
	}

	@Path("{productDescriptionUuid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchdProductDescriptionByUuid(
	        @PathParam("productDescriptionUuid") final String productDescriptionUuid) throws BusinessException {

		final ProductDescription productDescription = this.productDescriptionQueryService
		        .fetchProductDescriptionByUuid(productDescriptionUuid);

		return Response.ok(productDescription).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProductDescription(final ProductDescription productDescription) throws BusinessException {
		this.productDescriptionService.updateProductDescription(this.getContext(), productDescription);

		return Response.ok(productDescription).build();
	}

	@Path("{productDescriptionUuid}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductDescription(@PathParam("productDescriptionUuid") final String productDescriptionUuid)
	        throws BusinessException {
		final ProductDescription productDescription = this.productDescriptionQueryService
		        .fetchProductDescriptionByUuid(productDescriptionUuid);

		this.productDescriptionService.updateProductDescription(this.getContext(), productDescription);

		return Response.ok(productDescription).build();
	}
}
