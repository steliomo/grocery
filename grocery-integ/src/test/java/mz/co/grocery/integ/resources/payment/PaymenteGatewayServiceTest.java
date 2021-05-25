/**
 *
 */
package mz.co.grocery.integ.resources.payment;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import mz.co.grocery.integ.resources.payment.dto.MpesaRequestDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaResponseDTO;
import mz.co.grocery.integ.resources.payment.service.MpesaPaymentGatewayServieImpl;
import mz.co.grocery.integ.resources.payment.service.PaymentGatewayService;

/**
 * @author Stélio Moiane
 *
 */
public class PaymenteGatewayServiceTest {

	@Ignore
	@Test
	public void shouldMakeMpasaPayment() {

		final Mpesa mpesa = new Mpesa("https://api.sandbox.vm.co.mz:18352/ipg/v1x/c2bPayment/singleStage/",
				"807zwrhuchrp03fspf958am45ioog8ih",
				"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmptSWqV7cGUUJJhUBxsMLonux24u+FoTlrb+4Kgc6092JIszmI1QUoMohaDDXSVueXx6IXwYGsjjWY32HGXj1iQhkALXfObJ4DqXn5h6E8y5/xQYNAyd5bpN5Z8r892B6toGzZQVB7qtebH4apDjmvTi5FGZVjVYxalyyQkj4uQbbRQjgCkubSi45Xl4CGtLqZztsKssWz3mcKncgTnq3DHGYYEYiKq0xIj100LGbnvNz20Sgqmw/cH+Bua4GJsWYLEqf/h/yiMgiBbxFxsnwZl0im5vXDlwKPw+QnO2fscDhxZFAwV06bgG0oEoWm9FnjMsfvwm0rUNYFlZ+TOtCEhmhtFp+Tsx9jPCuOd5h2emGdSKD8A6jtwhNa7oQ8RtLEEqwAn44orENa1ibOkxMiiiFpmmJkwgZPOG/zMCjXIrrhDWTDUOZaPx/lEQoInJoE2i43VN/HTGCCw8dKQAwg0jsEXau5ixD0GUothqvuX3B9taoeoFAIvUPEq35YulprMM7ThdKodSHvhnwKG82dCsodRwY428kg2xM/UjiTENog4B6zzZfPhMxFlOSFX4MnrqkAS+8Jamhy1GgoHkEMrsT5+/ofjCx0HjKbT5NuA2V/lmzgJLl3jIERadLzuTYnKGWxVJcGLkWXlEPYLbiaKzbJb2sYxt+Kt5OxQqC1MCAwEAAQ==",
				"NM2021", "171717");

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		final PaymentGatewayService<MpesaRequestDTO, MpesaResponseDTO> paymentGatewayServie = new MpesaPaymentGatewayServieImpl(mpesa);

		final MpesaResponseDTO mpesaResponseDTO = paymentGatewayServie
				.makePayment(new MpesaRequestDTO("T12344C", "840546824", "100", mpesa.getThirtPartyReference(), mpesa.getProviderCode()));

		Assert.assertEquals("INS-0", mpesaResponseDTO.getOutput_ResponseCode());
		Assert.assertEquals("Request processed successfully", mpesaResponseDTO.getOutput_ResponseDesc());
	}
}
