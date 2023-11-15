/**
 *
 */
package mz.co.grocery.persistence.pos.adapter;

import mz.co.grocery.core.application.pos.out.SendMessagesPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.pos.Message;
import mz.co.grocery.persistence.pos.WhatsApp;
import mz.co.grocery.persistence.pos.model.Language;
import mz.co.grocery.persistence.pos.model.WhatsAppComponent;
import mz.co.grocery.persistence.pos.model.WhatsAppDocument;
import mz.co.grocery.persistence.pos.model.WhatsAppMessage;
import mz.co.grocery.persistence.pos.model.WhatsAppParameter;
import mz.co.grocery.persistence.pos.model.WhatsAppTemplate;
import mz.co.grocery.persistence.pos.service.WhatsAppAPIService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class SendWhatsAppMessageAdapter implements SendMessagesPort {

	private WhatsAppAPIService whatsAppAPIService;

	private WhatsApp whatsApp;

	public SendWhatsAppMessageAdapter(final WhatsAppAPIService whatsAppAPIService, final WhatsApp whatsApp) {
		this.whatsAppAPIService = whatsAppAPIService;
		this.whatsApp = whatsApp;
	}

	@Override
	public void sendMessages(final Message... messages) throws BusinessException {

		for (final Message message : messages) {

			switch (message.getType()) {

			case TEXT:

				final WhatsAppParameter parameter = new WhatsAppParameter(this.whatsApp.getParameterType(), message.getCustomer());

				final WhatsAppComponent component = new WhatsAppComponent(this.whatsApp.getComponentType());
				component.addParameter(parameter);

				final WhatsAppTemplate template = new WhatsAppTemplate(this.whatsApp.getBill(), new Language(this.whatsApp.getLanguage()));
				template.addComponent(component);

				final WhatsAppMessage textMessage = new WhatsAppMessage(this.whatsApp.getProduct(), message.getTo(), this.whatsApp.getTextType(),
						template);

				this.whatsAppAPIService.sendMessage(textMessage);
				break;

			default:

				final WhatsAppDocument document = new WhatsAppDocument(message.getDocumentName(), this.whatsApp.getDocumentUrl());
				final WhatsAppMessage documentMessage = new WhatsAppMessage(this.whatsApp.getProduct(), message.getTo(),
						this.whatsApp.getDocumentType(),
						document);

				this.whatsAppAPIService.sendMessage(documentMessage);
			}
		}
	}
}
