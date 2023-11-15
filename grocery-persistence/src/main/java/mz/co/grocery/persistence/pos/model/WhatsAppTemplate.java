/**
 *
 */
package mz.co.grocery.persistence.pos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class WhatsAppTemplate {

	private String name;

	private Language language;

	private List<WhatsAppComponent> components;

	public WhatsAppTemplate() {
	}

	public WhatsAppTemplate(final String name, final Language language) {
		this.name = name;
		this.language = language;
		this.components = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public Language getLanguage() {
		return this.language;
	}

	public List<WhatsAppComponent> getComponents() {
		return this.components;
	}

	public void addComponent(final WhatsAppComponent component) {
		this.components.add(component);
	}
}
