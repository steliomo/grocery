/**
 *
 */
package mz.co.grocery.core.domain.email;

import java.util.HashMap;
import java.util.Map;

import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class EmailDetails {

	private Unit unit;

	private EmailType emailType;

	private String attachment;

	private Map<String, Object> params;

	public EmailDetails(final Unit unit, final EmailType emailType, final String attachment) {
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

	public String getAttachment() {
		return this.attachment;
	}

	public Map<String, Object> getParams() {
		return this.params;
	}

	public void setParam(final String key, final Object value) {
		this.params.put(key, value);
	}
}
