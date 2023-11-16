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

	private String contact;

	private MessageType type;

	private String documentName;

	private Boolean hasUrlText;

	public Message(final String contact, final MessageType type, final String documentName) {
		this.contact = contact;
		this.type = type;
		this.documentName = documentName;
	}

	public Message(final String customer, final String contact, final MessageType type, final Boolean hasUrlText) {
		this.customer = customer;
		this.contact = contact;
		this.type = type;
		this.hasUrlText = hasUrlText;
	}

	public String getContact() {
		return this.contact;
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

	public Boolean hasUrlText() {
		return this.hasUrlText;
	}
}
