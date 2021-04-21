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
		final PaymentGatewayService<MpesaRequestDTO, MpesaResponseDTO> paymentGatewayServie = new MpesaPaymentGatewayServieImpl();

		final MpesaResponseDTO mpesaResponseDTO = paymentGatewayServie
				.makePayment(new MpesaRequestDTO("T12344C", "258840546824", "28", "3BPYIP", "171717"));

		Assert.assertEquals("INS-0", mpesaResponseDTO.getOutput_ResponseCode());
		Assert.assertEquals("Request processed successfully", mpesaResponseDTO.getOutput_ResponseDesc());
	}
}
