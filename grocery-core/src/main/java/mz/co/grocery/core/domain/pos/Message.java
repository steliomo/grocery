/**
 *
 */
package mz.co.grocery.core.domain.pos;

/**
 * @author Stélio Moiane
 *
 */

public class Message {

	private String customer;

	private String to;

	private MessageType type;

	private String documentName;

	public Message(final String to, final MessageType type, final String documentName) {
		this.to = to;
		this.type = type;
		this.documentName = documentName;
	}

	public Message(final String to, final String customer, final MessageType type) {
		this.to = to;
		this.type = type;
		this.customer = customer;
	}

	public String getTo() {
		return this.to;
	}

	public MessageType getType() {
		return this.type;
	}

	public String getDocumentName() {
		return this.documentName;
	}

	public String getCustomer() {
		return this.customer;
	}
}
