/**
 *
 */
package mz.co.grocery.integ.resources.mail;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Service(MailSenderService.NAME)
public class MailSenderService {

	public static final String NAME = "mz.co.grocery.integ.resources.mail.MailSenderServiceImpl";

	@Inject
	private JavaMailSender mailSender;

	@Autowired
	private Configuration configuration;

	public void send(final Mail mail) throws BusinessException {

		this.mailSender.send(mimeMessage -> {
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, Boolean.TRUE);

			mimeMessageHelper.setFrom(mail.getMailFrom());
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setText(this.getContentFromTemplate(mail), Boolean.TRUE);

			this.mailSender.send(mimeMessageHelper.getMimeMessage());

		});
	}

	private String getContentFromTemplate(final Mail mail) {

		final StringBuffer content = new StringBuffer();

		try {
			content
			.append(FreeMarkerTemplateUtils.processTemplateIntoString(
					this.configuration.getTemplate(mail.getMailTemplate()),
					mail.getParams()));
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}

		return content.toString();
	}
}
