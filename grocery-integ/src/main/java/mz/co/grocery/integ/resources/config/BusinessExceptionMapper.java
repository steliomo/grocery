/**
 *
 */
package mz.co.grocery.integ.resources.config;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	private ApplicationTranslator translator;

	public BusinessExceptionMapper() {
	}

	@Inject
	public BusinessExceptionMapper(final ApplicationTranslator translator) {
		this.translator = translator;
	}

	@Override
	public Response toResponse(final BusinessException exception) {
		String message = exception.getMessage();

		if (!message.contains(" ")) {
			message = this.translator.getTranslation(message);
		}

		final ErrorMessage errorMessage = new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
				message, "There was and issue in the business logic");

		return Response.status(errorMessage.getStatusCode()).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	}
}
