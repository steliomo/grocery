/**
 *
 */
package mz.co.grocery.integ.resources.mail;

import java.io.File;
import java.io.IOException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Service(MailSenderService.NAME)
public class MailSenderService implements EmailPort {

	public static final String NAME = "mz.co.grocery.integ.resources.mail.MailSenderServiceImpl";

	private JavaMailSender mailSender;

	private Configuration configuration;

	private Mail mail;

	private ApplicationTranslator translator;

	public MailSenderService(final JavaMailSender mailSender, final Configuration configuration, final Mail mail,
			final ApplicationTranslator translator) {
		this.mailSender = mailSender;
		this.configuration = configuration;
		this.mail = mail;
		this.translator = translator;
	}

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

	@Override
	public void send(final EmailDetails email) throws BusinessException {

		this.mailSender.send(mimeMessage -> {
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, Boolean.TRUE);

			mimeMessageHelper.setFrom(this.mail.getMailFrom());
			mimeMessageHelper.setTo(email.getUnit().getEmail());
			mimeMessageHelper.setSubject(this.translator.getTranslation(email.getEmailType().getLabel()));

			final StringBuilder builder = new StringBuilder();
			builder.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(this.configuration.getTemplate(email.getEmailType().getTemplate()), email.getParams()));

			mimeMessageHelper.setText(builder.toString(), Boolean.TRUE);

			mimeMessageHelper.addAttachment(email.getAttachment(), new File(DocumentGeneratorPort.FILE_DIR + email.getAttachment()));

			this.mailSender.send(mimeMessageHelper.getMimeMessage());

		});

	}
}
