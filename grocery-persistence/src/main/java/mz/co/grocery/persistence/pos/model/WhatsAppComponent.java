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
public class WhatsAppComponent {

	private String type;

	private List<WhatsAppParameter> parameters;

	public WhatsAppComponent() {
	}

	public WhatsAppComponent(final String type) {
		this.type = type;
		this.parameters = new ArrayList<>();
	}

	public String getType() {
		return this.type;
	}

	public List<WhatsAppParameter> getParameters() {
		return this.parameters;
	}

	public void addParameter(final WhatsAppParameter parameter) {
		this.parameters.add(parameter);
	}
}
