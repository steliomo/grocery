/**
 *
 */
package mz.co.grocery.integ.resources.util;

/**
 * @author Stélio Moiane
 *
 */
public class EnumDTO {

	private final String value;

	private final String label;

	public EnumDTO(final String value, final String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabel() {
		return this.label;
	}
}
