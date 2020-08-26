/**
 *
 */
package mz.co.grocery.integ.resources.config;

/**
 * @author Stélio Moiane
 *
 */
public class ErrorMessage {

	private int statusCode;

	private String message;

	private String developerMessage;

	public ErrorMessage() {
	}

	public ErrorMessage(final int statusCode, final String message, final String developerMessage) {
		this.statusCode = statusCode;
		this.message = message;
		this.developerMessage = developerMessage;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getMessage() {
		return this.message;
	}

	public String getDeveloperMessage() {
		return this.developerMessage;
	}
}
