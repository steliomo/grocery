/**
 *
 */
package mz.co.grocery.persistence.pos.model;

/**
 * @author Stélio Moiane
 *
 */
public class WhatsAppText {

	private boolean preview_url;

	private String body;

	public WhatsAppText() {
	}

	public WhatsAppText(final boolean preview_url, final String body) {
		this.preview_url = preview_url;
		this.body = body;
	}

	public boolean isPreview_url() {
		return this.preview_url;
	}

	public String getBody() {
		return this.body;
	}
}
