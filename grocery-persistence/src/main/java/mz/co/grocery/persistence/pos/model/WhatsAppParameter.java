/**
 *
 */
package mz.co.grocery.persistence.pos.model;

/**
 * @author Stélio Moiane
 *
 */
public class WhatsAppParameter {

	private String type;

	private String text;

	public WhatsAppParameter() {
	}

	public WhatsAppParameter(final String type, final String text) {
		this.type = type;
		this.text = text;
	}

	public String getType() {
		return this.type;
	}

	public String getText() {
		return this.text;
	}
}
