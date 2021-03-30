/**
 *
 */
package mz.co.grocery.integ.resources.item;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.item.model.Product;
import mz.co.grocery.core.item.service.ProductQueryService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.item.dto.ProductDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("products")
@Service(ProductResource.NAME)
public class ProductResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.item.ProductResource";

	@Inject
	private ProductService producService;

	@Inject
	private ProductQueryService productQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProduct(final ProductDTO productDTO) throws BusinessException {
		this.producService.createProduct(this.getContext(), productDTO.get());
		return Response.ok(productDTO).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProducts() throws BusinessException {
		final List<Product> products = this.productQueryService.findAllProducts();

		final List<ProductDTO> productsDTO = products.stream().map(product -> new ProductDTO(product))
				.collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}

	@GET
	@Path("by-name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductsByName(@PathParam("name") final String name) throws BusinessException {
		final List<Product> products = this.productQueryService.findProductByName(name);

		final List<ProductDTO> productsDTO = products.stream().map(product -> new ProductDTO(product))
				.collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(final ProductDTO productDTO) throws BusinessException {
		final Product product = this.producService.uppdateProduct(this.getContext(), productDTO.get());
		return Response.ok(new ProductDTO(product)).build();
	}

	@GET
	@Path("{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductByUuid(@PathParam("productUuid") final String productUuid) throws BusinessException {
		final Product product = this.productQueryService.findProductByUuid(productUuid);
		return Response.ok(new ProductDTO(product)).build();
	}

	@GET
	@Path("by-grocery/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductByGroceryUuid(@PathParam("groceryUuid") final String groceryUuid)
			throws BusinessException {

		final GroceryDTO groceryDTO = new GroceryDTO(groceryUuid);
		final List<Product> products = this.productQueryService.findProductsByGrocery(groceryDTO.get());

		final List<ProductDTO> productsDTO = products.stream().map(product -> new ProductDTO(product))
				.collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}

	@DELETE
	@Path("{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeProduct(@PathParam("productUuid") final String productUuid) throws BusinessException {
		final Product product = this.productQueryService.findProductByUuid(productUuid);
		this.producService.removeProduct(this.getContext(), product);
		return Response.ok(new ProductDTO(product)).build();
	}

	@GET
	@Path("not-in-this-grocery/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductsNotInThisGroceryUuid(@PathParam("groceryUuid") final String groceryUuid)
			throws BusinessException {

		final GroceryDTO groceryDTO = new GroceryDTO(groceryUuid);
		final List<Product> products = this.productQueryService.findProductsNotInThisGrocery(groceryDTO.get());

		final List<ProductDTO> productsDTO = products.stream().map(product -> new ProductDTO(product))
				.collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}
}
