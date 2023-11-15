/**
 *
 */
package mz.co.grocery.persistence.pos.model;

/**
 * @author Stélio Moiane
 *
 */
public class WhatsAppDocument {

	private String filename;

	private String link;

	public WhatsAppDocument() {
	}

	public WhatsAppDocument(final String filename, final String link) {
		this.filename = filename;
		this.link = link + filename;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getLink() {
		return this.link;
	}
}
