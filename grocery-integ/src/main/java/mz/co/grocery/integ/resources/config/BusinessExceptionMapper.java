/**
 *
 */
package mz.co.grocery.integ.resources.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(final BusinessException exception) {

		final ErrorMessage errorMessage = new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),
				exception.getMessage(), "There was and issue in the business logic");

		return Response.status(errorMessage.getStatusCode()).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	}
}
