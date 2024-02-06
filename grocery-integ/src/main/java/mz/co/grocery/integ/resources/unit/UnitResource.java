/**
 *
 */
package mz.co.grocery.integ.resources.unit;

import java.io.InputStream;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.unit.in.UploadUnitLogoUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.domain.unit.UnitType;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.common.EnumsDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("units")
@WebAdapter
public class UnitResource extends AbstractResource {

	@Inject
	private UnitPort unitPort;

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private UploadUnitLogoUseCase uploadUnitLogoUseCase;

	@Autowired
	private DTOMapper<UnitDTO, Unit> unitMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGrocery(final Unit unit) throws BusinessException {
		this.unitPort.createUnit(this.getContext(), unit);
		return Response.ok(unit).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findGroceries(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final Long totalItems = this.unitPort.count();
		final List<Unit> groceries = this.unitPort.findAllGroceries(currentPage, maxResult);

		return Response.ok(new UnitsDTO(groceries, totalItems, this.unitMapper)).build();
	}

	@GET
	@Path("by-name")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUnitsByName(@QueryParam("unitName") final String unitName) throws BusinessException {

		final List<Unit> units = this.unitPort.findUnitsByName(unitName);
		final Long totalItems = this.unitPort.count();

		return Response.ok(new UnitsDTO(units, totalItems, this.unitMapper)).build();
	}

	@GET
	@Path("unit-types")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUnitTypes() throws BusinessException {
		final EnumsDTO<UnitType> enumsDTO = new EnumsDTO<>(this.translator, UnitType.values());
		return Response.ok(enumsDTO).build();
	}

	@POST
	@Path("logo-upload/{unitUuid}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadUnitLogo(@FormDataParam("file") final InputStream uploadedInputStream,
			@FormDataParam("file") final FormDataContentDisposition fileDetails, @PathParam("unitUuid") final String unitUuid)
					throws BusinessException {

		final Unit unit = this.uploadUnitLogoUseCase.uploadLogo(this.getContext(), uploadedInputStream, fileDetails.getFileName(), unitUuid);

		return Response.ok(this.unitMapper.toDTO(unit)).build();
	}
}
