/**
 *
 */
package mz.co.grocery.integ.resources.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Stélio Moiane
 *
 */
@Component(Mail.NAME)
public class Mail {

	public static final String NAME = "mz.co.grocery.integ.resources.mail";

	@Value("${spring.mail.username}")
	private String mailFrom;

	@Value("${mail.reset.password}")
	private String resetPassword;

	@Value("${mail.welcome}")
	private String wellcome;

	private String mailTemplate;

	private String mailTo;

	private String mailCc;

	private String mailBcc;

	private String mailSubject;

	private String mailContent;

	private List<Object> attachments;

	private Map<String, Object> params;

	public String getMailFrom() {
		return this.mailFrom;
	}

	public String getResetPassword() {
		return this.resetPassword;
	}

	public String getWelcome() {
		return this.wellcome;
	}

	public String getMailTemplate() {
		return this.mailTemplate;
	}

	public void setMailTemplate(final String mainTemplate) {
		this.mailTemplate = mainTemplate;
	}

	public String getMailTo() {
		return this.mailTo;
	}

	public void setMailTo(final String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCc() {
		return this.mailCc;
	}

	public void setMailCc(final String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailBcc() {
		return this.mailBcc;
	}

	public void setMailBcc(final String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getMailSubject() {
		return this.mailSubject;
	}

	public void setMailSubject(final String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContent() {
		return this.mailContent;
	}

	public void setMailContent(final String mailContent) {
		this.mailContent = mailContent;
	}

	public List<Object> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final List<Object> attachments) {
		this.attachments = attachments;
	}

	public Map<String, Object> getParams() {
		return this.params;
	}

	public void setParam(final String key, final Object value) {
		if (this.params == null) {
			this.params = new HashMap<>();
		}

		this.params.put(key, value);
	}
}
