/**
 *
 */
package mz.co.grocery.integ.resources.report;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@Path("files")
@WebAdapter
public class ReportResource {

	@Path("{fileName}")
	@GET
	@Produces("application/pdf")
	public Response loadPdfFile(@PathParam("fileName") final String fileName) throws BusinessException {
		final File file = new File(DocumentGeneratorPort.FILE_DIR + fileName);
		return Response.ok(file).header("Content-Disposition", "attachment; filename=" + fileName).build();
	}
}
