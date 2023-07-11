/**
 *
 */
package mz.co.grocery.integ.resources.item;

import java.util.Arrays;
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

import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.item.ProductUnitType;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.item.dto.ProductUnitDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("product-units")
@WebAdapter
public class ProductUnitResource extends AbstractResource {

	@Inject
	private ProductUnitPort productUnitPort;

	@Autowired
	private DTOMapper<ProductUnitDTO, ProductUnit> productUnitMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProductUnit(final ProductUnitDTO productUnitDTO) throws BusinessException {
		ProductUnit productUnit = this.productUnitMapper.toDomain(productUnitDTO);

		productUnit = this.productUnitPort.createProductUnit(this.getContext(), productUnit);

		return Response.ok(this.productUnitMapper.toDTO(productUnit)).build();
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
		final List<ProductUnit> productUnits = this.productUnitPort.findAllProductUnits();

		final List<ProductUnitDTO> productUnitsDTO = productUnits.stream()
				.map(productUnit -> this.productUnitMapper.toDTO(productUnit)).collect(Collectors.toList());

		return Response.ok(productUnitsDTO).build();
	}

	@Path("{productUnitUuid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductUnitByUuid(@PathParam("productUnitUuid") final String productUnitUuid)
			throws BusinessException {

		final ProductUnit productUnit = this.productUnitPort.findProductUnitByUuid(productUnitUuid);

		return Response.ok(this.productUnitMapper.toDTO(productUnit)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProductUnit(final ProductUnitDTO productUnitDTO) throws BusinessException {
		ProductUnit productUnit = this.productUnitMapper.toDomain(productUnitDTO);

		productUnit = this.productUnitPort.updateProductUnit(this.getContext(), productUnit);

		return Response.ok(this.productUnitMapper.toDTO(productUnit)).build();
	}

	@Path("{productUnitUuid}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductUnit(@PathParam("productUnitUuid") final String productUnitUuid)
			throws BusinessException {

		ProductUnit productUnit = this.productUnitPort.findProductUnitByUuid(productUnitUuid);

		productUnit = this.productUnitPort.removeProductUnit(this.getContext(), productUnit);

		return Response.ok(this.productUnitMapper.toDTO(productUnit)).build();
	}
}