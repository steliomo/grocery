/**
 *
 */
package mz.co.grocery.integ.resources.files;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.file.service.FileGeneratorService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("files")
@Service(FileResource.NAME)
public class FileResource {

	public static final String NAME = "mz.co.grocery.integ.resources.files.FileResource";

	@Path("{fileName}")
	@GET
	@Produces("application/pdf")
	public Response loadPdfFile(@PathParam("fileName") final String fileName) throws BusinessException {
		final File file = new File(FileGeneratorService.FILE_DIR + fileName);
		return Response.ok(file).header("Content-Disposition", "attachment; filename=" + fileName).build();
	}
}
