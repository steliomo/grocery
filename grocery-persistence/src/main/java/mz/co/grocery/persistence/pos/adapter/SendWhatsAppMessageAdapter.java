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
import mz.co.grocery.persistence.pos.model.WhatsAppText;
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

			case TEMPLATE:

				final WhatsAppParameter parameter = new WhatsAppParameter(this.whatsApp.getParameterType(), message.getCustomer());

				final WhatsAppComponent component = new WhatsAppComponent(this.whatsApp.getComponentType());
				component.addParameter(parameter);

				final WhatsAppTemplate template = new WhatsAppTemplate(this.whatsApp.getBill(), new Language(this.whatsApp.getLanguage()));
				template.addComponent(component);

				final WhatsAppMessage templateMessage = new WhatsAppMessage(this.whatsApp.getProduct(), message.getContact(),
						this.whatsApp.getTemplateType(),
						template);

				this.whatsAppAPIService.sendMessage(templateMessage);
				break;

			case DOCUMENT:

				final WhatsAppDocument document = new WhatsAppDocument(message.getDocumentName(), this.whatsApp.getDocumentUrl());
				final WhatsAppMessage documentMessage = new WhatsAppMessage(this.whatsApp.getProduct(), message.getContact(),
						this.whatsApp.getDocumentType(),
						document);

				this.whatsAppAPIService.sendMessage(documentMessage);
				break;

			default:

				final String messageContent = String.format(this.whatsApp.getBill(), message.getCustomer());

				final WhatsAppText text = new WhatsAppText(message.hasUrlText(), messageContent);

				final WhatsAppMessage textMessage = new WhatsAppMessage(this.whatsApp.getProduct(), this.whatsApp.getRecipient(),
						message.getContact(),
						this.whatsApp.getTextType(),
						text);

				this.whatsAppAPIService.sendMessage(textMessage);
			}
		}
	}
}
