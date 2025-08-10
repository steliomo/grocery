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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.unit.in.VerifySubscriptionValidatyUseCase;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.item.dto.ProductDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("products")
@WebAdapter
public class ProductResource extends AbstractResource {

	@Inject
	private ProductPort productPort;

	@Autowired
	private DTOMapper<ProductDTO, Product> productMapper;

	@Autowired
	private DTOMapper<UnitDTO, Unit> unitMapper;

	@Inject
	private VerifySubscriptionValidatyUseCase verifySubscriptionValidatyUseCase;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProduct(final ProductDTO productDTO) throws BusinessException {

		Product product = this.productMapper.toDomain(productDTO);

		product = this.productPort.createProduct(this.getContext(), product);

		return Response.ok(this.productMapper.toDTO(product)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProducts() throws BusinessException {
		final List<Product> products = this.productPort.findAllProducts();

		final List<ProductDTO> productsDTO = products.stream().map(product -> this.productMapper.toDTO(product)).collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}

	@GET
	@Path("by-name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductsByName(@PathParam("name") final String name) throws BusinessException {
		final List<Product> products = this.productPort.findProductByName(name);

		final List<ProductDTO> productsDTO = products.stream().map(product -> this.productMapper.toDTO(product)).collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(final ProductDTO productDTO) throws BusinessException {

		Product product = this.productMapper.toDomain(productDTO);

		product = this.productPort.uppdateProduct(this.getContext(), product);

		return Response.ok(this.productMapper.toDTO(product)).build();
	}

	@GET
	@Path("{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductByUuid(@PathParam("productUuid") final String productUuid) throws BusinessException {

		final Product product = this.productPort.findProductByUuid(productUuid);

		return Response.ok(this.productMapper.toDTO(product)).build();
	}

	@GET
	@Path("by-grocery/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductByGroceryUuid(@PathParam("groceryUuid") final String unitUuid)
			throws BusinessException {

		this.verifySubscriptionValidatyUseCase.isSubscriptionValid(unitUuid);

		final UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUuid(unitUuid);

		final List<Product> products = this.productPort.findProductsByGrocery(this.unitMapper.toDomain(unitDTO));

		final List<ProductDTO> productsDTO = products.stream().map(product -> this.productMapper.toDTO(product)).collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}

	@DELETE
	@Path("{productUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeProduct(@PathParam("productUuid") final String productUuid) throws BusinessException {
		Product product = this.productPort.findProductByUuid(productUuid);

		product = this.productPort.removeProduct(this.getContext(), product);

		return Response.ok(this.productMapper.toDTO(product)).build();
	}

	@GET
	@Path("not-in-this-grocery/{groceryUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductsNotInThisGroceryUuid(@PathParam("groceryUuid") final String unitUuid)
			throws BusinessException {

		final UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUuid(unitUuid);

		final List<Product> products = this.productPort.findProductsNotInThisGrocery(this.unitMapper.toDomain(unitDTO));

		final List<ProductDTO> productsDTO = products.stream().map(product -> this.productMapper.toDTO(product))
				.collect(Collectors.toList());

		return Response.ok(productsDTO).build();
	}
}
