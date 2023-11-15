/**
 *
 */
package mz.co.grocery.persistence.pos.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.persistence.pos.WhatsApp;
import mz.co.grocery.persistence.pos.model.WhatsAppMessage;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@Service
public class WhatsAppAPIServiceImpl implements WhatsAppAPIService {

	private Client client;

	private WhatsApp whatsApp;

	public WhatsAppAPIServiceImpl(final WhatsApp whatsApp) {
		this.client = ClientBuilder.newClient();
		this.whatsApp = whatsApp;
	}

	@Override
	public WhatsAppMessage sendMessage(final WhatsAppMessage message) throws BusinessException {
		final Response response = this.client
				.target(
						this.whatsApp.getUrl())
				.request(MediaType.APPLICATION_JSON)
				.header("Origin", "*")
				.header(HttpHeaders.AUTHORIZATION, this.whatsApp.getToken())
				.post(Entity.entity(message, MediaType.APPLICATION_JSON));

		return response.readEntity(WhatsAppMessage.class);
	}
}
