/**
 *
 */
package mz.co.grocery.persistence.pos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class WhatsApp {

	@Value("${whatsapp.message.url}")
	private String url;

	@Value("${whatsapp.message.recipient.type}")
	private String recipient;

	@Value("${whatsapp.message.token}")
	private String token;

	@Value("${whatsapp.message.product}")
	private String product;

	@Value("${whatsapp.message.welcome}")
	private String welcome;

	@Value("${whatsapp.message.bill}")
	private String bill;

	@Value("${whatsapp.message.template.bill}")
	private String templateBill;

	@Value("${whatsapp.message.receipt}")
	private String receipt;

	@Value("${whatsapp.message.language}")
	private String language;

	@Value("${whatsapp.message.text.type}")
	private String textType;

	@Value("${whatsapp.message.document.type}")
	private String documentType;

	@Value("${whatsapp.message.template.type}")
	private String templateType;

	@Value("${document.url}")
	private String documentUrl;

	@Value("${whatsapp.component.body.type}")
	private String bodyType;

	@Value("${whatsapp.component.header.type}")
	private String headerType;

	@Value("${whatsapp.component.text.parameter.type}")
	private String textParameterType;

	public String getUrl() {
		return this.url;
	}

	public String getToken() {
		return this.token;
	}

	public String getProduct() {
		return this.product;
	}

	public String getRecipient() {
		return this.recipient;
	}

	public String getWelcome() {
		return this.welcome;
	}

	public String getBill() {
		return this.bill;
	}

	public String getTemplateBill() {
		return this.templateBill;
	}

	public String getReceipt() {
		return this.receipt;
	}

	public String getLanguage() {
		return this.language;
	}

	public String getTextType() {
		return this.textType;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public String getHeaderType() {
		return this.headerType;
	}

	public String getBodyType() {
		return this.bodyType;
	}

	public String getTextParameterType() {
		return this.textParameterType;
	}
}
