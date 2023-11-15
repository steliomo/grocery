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

	@Value("${whatsapp.message.token}")
	private String token;

	@Value("${whatsapp.message.product}")
	private String product;

	@Value("${whatsapp.message.welcome}")
	private String welcome;

	@Value("${whatsapp.message.bill}")
	private String bill;

	@Value("${whatsapp.message.receipt}")
	private String receipt;

	@Value("${whatsapp.message.language}")
	private String language;

	@Value("${whatsapp.message.text.type}")
	private String textType;

	@Value("${whatsapp.message.document.type}")
	private String documentType;

	@Value("${document.url}")
	private String documentUrl;

	@Value("${whatsapp.component.type}")
	private String componentType;

	@Value("${whatsapp.component.parameter.type}")
	private String parameterType;

	public String getUrl() {
		return this.url;
	}

	public String getToken() {
		return this.token;
	}

	public String getProduct() {
		return this.product;
	}

	public String getWelcome() {
		return this.welcome;
	}

	public String getBill() {
		return this.bill;
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

	public String getDocumentType() {
		return this.documentType;
	}

	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public String getComponentType() {
		return this.componentType;
	}

	public String getParameterType() {
		return this.parameterType;
	}
}
