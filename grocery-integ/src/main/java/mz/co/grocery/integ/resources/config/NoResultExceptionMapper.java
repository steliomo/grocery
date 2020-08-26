/**
 *
 */
package mz.co.grocery.integ.resources.config;

import javax.persistence.NoResultException;
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
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {

	@Override
	public Response toResponse(final NoResultException exception) {
		final ErrorMessage errorMessage = new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),
				"Entity not found",
				ExceptionUtils.getRootCauseMessage(exception));

		return Response.status(errorMessage.getStatusCode()).entity(errorMessage).type(MediaType.APPLICATION_JSON)
				.build();
	}

}
