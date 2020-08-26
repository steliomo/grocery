/**
 *
 */
package mz.co.grocery.integ.resources.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author Stélio Moiane
 *
 */
@Provider
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(final Throwable exception) {
		final ErrorMessage errorMessage = new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
				"There was an internal error in the server", ExceptionUtils.getRootCauseMessage(exception));

		return Response.status(errorMessage.getStatusCode()).entity(errorMessage).type(MediaType.APPLICATION_JSON)
				.build();
	}

}
