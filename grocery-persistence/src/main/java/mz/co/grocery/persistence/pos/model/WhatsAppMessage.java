/**
 *
 */
package mz.co.grocery.persistence.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Stélio Moiane
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class WhatsAppMessage {

	private String messaging_product;

	private String recipient_type;

	private String to;

	private String type;

	private WhatsAppText text;

	private WhatsAppDocument document;

	private WhatsAppTemplate template;

	public WhatsAppMessage() {
	}

	public WhatsAppMessage(final String messaging_product, final String recipient_type, final String to, final String type, final WhatsAppText text) {
		this.messaging_product = messaging_product;
		this.recipient_type = recipient_type;
		this.to = to;

		this.type = type;
		this.text = text;
	}

	public WhatsAppMessage(final String messaging_product, final String to, final String type, final WhatsAppTemplate template) {
		this.messaging_product = messaging_product;
		this.to = to;
		this.type = type;
		this.template = template;
	}

	public WhatsAppMessage(final String messaging_product, final String to, final String type, final WhatsAppDocument document) {
		this.messaging_product = messaging_product;
		this.to = to;
		this.type = type;
		this.document = document;
	}

	public String getMessaging_product() {
		return this.messaging_product;
	}

	public String getRecipient_type() {
		return this.recipient_type;
	}

	public String getTo() {
		return this.to;
	}

	public String getType() {
		return this.type;
	}

	public WhatsAppText getText() {
		return this.text;
	}

	public WhatsAppDocument getDocument() {
		return this.document;
	}

	public WhatsAppTemplate getTemplate() {
		return this.template;
	}
}
