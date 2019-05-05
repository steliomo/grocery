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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.service.ProductQueryService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("products")
@Service(ProductResource.NAME)
public class ProductResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.product.ProductResource";

	@Inject
	private ProductService producService;

	@Inject
	private ProductQueryService productQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProduct(final Product product) throws BusinessException {
		this.producService.createProduct(this.getContext(), product);
		return Response.ok(product).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProducts() throws BusinessException {
		final List<Product> products = this.productQueryService.findAllProducts();
		return Response.ok(products).build();
	}

	@GET
	@Path("by-name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductsByName(@PathParam("name") final String name) throws BusinessException {
		final List<Product> products = this.productQueryService.findProductByName(name);
		return Response.ok(products).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(final Product product) throws BusinessException {
		this.producService.uppdateProduct(this.getContext(), product);
		return Response.ok(product).build();
	}

	@GET
	@Path("{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductByUuid(@PathParam("productUuid") final String productUuid) throws BusinessException {
		final Product product = this.productQueryService.findProductByUuid(productUuid);
		return Response.ok(product).build();
	}

	@DELETE
	@Path("{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeProduct(@PathParam("productUuid") final String productUuid) throws BusinessException {
		final Product product = this.productQueryService.findProductByUuid(productUuid);
		this.producService.removeProduct(this.getContext(), product);
		return Response.ok(product).build();
	}
}
