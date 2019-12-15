/**
 *
 */
package mz.co.grocery.integ.resources.config;

/**
 * @author Stélio Moiane
 *
 */
public class ApiError {

	private int status;

	private String message;

	public ApiError() {
	}

	public ApiError(final int status, final String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return this.status;
	}

	public String getMessage() {
		return this.message;
	}
}
