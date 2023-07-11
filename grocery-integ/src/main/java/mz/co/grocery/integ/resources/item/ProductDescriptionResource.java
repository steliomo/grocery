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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.item.dto.ProductDescriptionDTO;
import mz.co.grocery.integ.resources.item.dto.ProductDescriptionsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("product-descriptions")
@WebAdapter
public class ProductDescriptionResource extends AbstractResource {

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Autowired
	private DTOMapper<ProductDescriptionDTO, ProductDescription> productDescriptionMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProductDescription(final ProductDescriptionDTO productDescriptionDTO)
			throws BusinessException {

		ProductDescription productDescription = this.productDescriptionMapper.toDomain(productDescriptionDTO);

		productDescription = this.productDescriptionPort.createProductDescription(this.getContext(), productDescription);

		return Response.ok(this.productDescriptionMapper.toDTO(productDescription)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProductDescriptions(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<ProductDescription> productDescriptions = this.productDescriptionPort
				.fetchdAllProductDescriptions(currentPage, maxResult);

		final Long totalItems = this.productDescriptionPort.countProductDescriptions();

		final ProductDescriptionsDTO productDescriptionDTO = new ProductDescriptionsDTO(productDescriptions,
				totalItems, this.productDescriptionMapper);

		return Response.ok(productDescriptionDTO).build();
	}

	@Path("by-description")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductDescriptionByDescription(@QueryParam("description") final String description)
			throws BusinessException {

		final List<ProductDescription> productDescriptions = this.productDescriptionPort
				.fetchProductDescriptionByDescription(description);

		final List<ProductDescriptionDTO> productDescriptionsDTO = productDescriptions.stream()
				.map(productDescription -> this.productDescriptionMapper.toDTO(productDescription)).collect(Collectors.toList());

		return Response.ok(productDescriptionsDTO).build();
	}

	@Path("{productDescriptionUuid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchdProductDescriptionByUuid(
			@PathParam("productDescriptionUuid") final String productDescriptionUuid) throws BusinessException {

		final ProductDescription productDescription = this.productDescriptionPort
				.fetchProductDescriptionByUuid(productDescriptionUuid);

		return Response.ok(this.productDescriptionMapper.toDTO(productDescription)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProductDescription(final ProductDescriptionDTO productDescriptionDTO)
			throws BusinessException {

		ProductDescription productDescription = this.productDescriptionMapper.toDomain(productDescriptionDTO);

		productDescription = this.productDescriptionPort.updateProductDescription(this.getContext(), productDescription);

		return Response.ok(this.productDescriptionMapper.toDTO(productDescription)).build();
	}

	@Path("{productDescriptionUuid}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductDescription(@PathParam("productDescriptionUuid") final String productDescriptionUuid)
			throws BusinessException {

		ProductDescription productDescription = this.productDescriptionPort
				.fetchProductDescriptionByUuid(productDescriptionUuid);

		productDescription = this.productDescriptionPort.updateProductDescription(this.getContext(), productDescription);

		return Response.ok(this.productDescriptionMapper.toDTO(productDescription)).build();
	}
}
