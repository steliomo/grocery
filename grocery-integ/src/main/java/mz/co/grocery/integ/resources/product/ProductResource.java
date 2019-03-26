/**
 *
 */
package mz.co.grocery.integ.resources.product;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.model.Product;
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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProduct(final Product product) throws BusinessException {

		this.producService.createProduct(this.getContext(), product);

		return Response.ok(product).build();
	}
}
