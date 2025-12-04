/**
 *
 */
package mz.co.grocery.core.domain.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class EmailDetails {

	private Unit unit;

	private EmailType emailType;

	private Optional<String> attachment;

	private Map<String, Object> params;

	private String customerName;

	private String customerEmail;

	public EmailDetails(final Unit unit, final EmailType emailType, final Optional<String> attachment) {
		this.unit = unit;
		this.emailType = emailType;
		this.attachment = attachment;

		this.params = new HashMap<>();

	}

	public Unit getUnit() {
		return this.unit;
	}

	public EmailType getEmailType() {
		return this.emailType;
	}

	public Optional<String> getAttachment() {
		return this.attachment;
	}

	public Map<String, Object> getParams() {
		return this.params;
	}

	public void setParam(final String key, final Object value) {
		this.params.put(key, value);
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(final String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(final String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
