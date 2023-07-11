/**
 *
 */
package mz.co.grocery.integ.resources.payment.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mz.co.grocery.integ.resources.common.BearerTokenUtil;
import mz.co.grocery.integ.resources.payment.Mpesa;
import mz.co.grocery.integ.resources.payment.dto.MpesaRequestDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaResponseDTO;

/**
 * @author Stélio Moiane
 *
 */
public class MpesaPaymentGatewayServieImpl implements PaymentGatewayService<MpesaRequestDTO, MpesaResponseDTO> {

	private final Mpesa mpesa;

	public MpesaPaymentGatewayServieImpl(final Mpesa mpesa) {
		this.mpesa = mpesa;
	}

	@Override
	public MpesaResponseDTO makePayment(final MpesaRequestDTO mpesaRequestDTO) {

		final Client client = ClientBuilder.newClient();

		final Response response = client
				.target(
						this.mpesa.getUrl())
				.request(MediaType.APPLICATION_JSON)
				.header("Origin", "*")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "
						+ BearerTokenUtil.getBearerToken(this.mpesa.getApiKey(), this.mpesa.getPublicKey()))
				.post(Entity.entity(mpesaRequestDTO, MediaType.APPLICATION_JSON));

		return response.readEntity(MpesaResponseDTO.class);
	}
}
