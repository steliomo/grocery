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

import mz.co.grocery.integ.resources.payment.dto.MpesaRequestDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaResponseDTO;
import mz.co.grocery.integ.resources.util.BearerTokenUtil;

/**
 * @author Stélio Moiane
 *
 */
public class MpesaPaymentGatewayServieImpl implements PaymentGatewayService<MpesaRequestDTO, MpesaResponseDTO> {

	@Override
	public MpesaResponseDTO makePayment(final MpesaRequestDTO mpesaRequestDTO) {

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		final Client client = ClientBuilder.newClient();

		final Response response = client
				.target(
						"https://api.sandbox.vm.co.mz:18352/ipg/v1x/c2bPayment/singleStage/")
				.request(MediaType.APPLICATION_JSON)
				.header("Origin", "*")
				.header(HttpHeaders.AUTHORIZATION, "Bearer "
						+
						BearerTokenUtil.getBearerToken("807zwrhuchrp03fspf958am45ioog8ih",
								"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmptSWqV7cGUUJJhUBxsMLonux24u+FoTlrb+4Kgc6092JIszmI1QUoMohaDDXSVueXx6IXwYGsjjWY32HGXj1iQhkALXfObJ4DqXn5h6E8y5/xQYNAyd5bpN5Z8r892B6toGzZQVB7qtebH4apDjmvTi5FGZVjVYxalyyQkj4uQbbRQjgCkubSi45Xl4CGtLqZztsKssWz3mcKncgTnq3DHGYYEYiKq0xIj100LGbnvNz20Sgqmw/cH+Bua4GJsWYLEqf/h/yiMgiBbxFxsnwZl0im5vXDlwKPw+QnO2fscDhxZFAwV06bgG0oEoWm9FnjMsfvwm0rUNYFlZ+TOtCEhmhtFp+Tsx9jPCuOd5h2emGdSKD8A6jtwhNa7oQ8RtLEEqwAn44orENa1ibOkxMiiiFpmmJkwgZPOG/zMCjXIrrhDWTDUOZaPx/lEQoInJoE2i43VN/HTGCCw8dKQAwg0jsEXau5ixD0GUothqvuX3B9taoeoFAIvUPEq35YulprMM7ThdKodSHvhnwKG82dCsodRwY428kg2xM/UjiTENog4B6zzZfPhMxFlOSFX4MnrqkAS+8Jamhy1GgoHkEMrsT5+/ofjCx0HjKbT5NuA2V/lmzgJLl3jIERadLzuTYnKGWxVJcGLkWXlEPYLbiaKzbJb2sYxt+Kt5OxQqC1MCAwEAAQ=="))
				.post(Entity.entity(mpesaRequestDTO, MediaType.APPLICATION_JSON));

		return response.readEntity(MpesaResponseDTO.class);
	}
}
