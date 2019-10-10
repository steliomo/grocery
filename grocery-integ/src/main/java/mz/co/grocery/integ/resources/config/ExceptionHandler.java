/**
 *
 */
package mz.co.grocery.integ.resources.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;

import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(final BusinessException exception) {
		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
		return Response.status(apiError.getStatus()).entity(apiError).type(MediaType.APPLICATION_JSON).build();
	}
}
