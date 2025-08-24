/**
 *
 */
package mz.co.grocery.core.domain.email;

/**
 * @author Stélio Moiane
 *
 */
public enum EmailType {

	DAILY_SALE("sale-daily-report-template.txt", "email.daily.sale");

	private String template;

	private String label;

	EmailType(final String template, final String label) {
		this.template = template;
		this.label = label;
	}

	public String getTemplate() {
		return this.template;
	}

	public String getLabel() {
		return this.label;
	}
}
