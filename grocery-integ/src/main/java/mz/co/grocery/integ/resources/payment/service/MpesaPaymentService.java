/**
 *
 */
package mz.co.grocery.integ.resources.payment.service;

import mz.co.grocery.core.application.payment.out.PaymentPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.payment.Mpesa;
import mz.co.grocery.integ.resources.payment.dto.MpesaRequestDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaResponseDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@WebAdapter
public class MpesaPaymentService implements PaymentPort {

	private static final String MPESA_SUCCESS = "INS-0";

	private Mpesa mpesa;

	private ApplicationTranslator translator;

	public MpesaPaymentService(final Mpesa mpesa, final ApplicationTranslator translator) {
		this.mpesa = mpesa;
		this.translator = translator;
	}

	@Override
	public void paySubscription(final UserContext context, final SubscriptionDetails subscriptionDetails) throws BusinessException {

		final PaymentGatewayService<MpesaRequestDTO, MpesaResponseDTO> paymentGatewayServie = new MpesaPaymentGatewayServieImpl(this.mpesa);
		final MpesaResponseDTO mpesaResponseDTO = paymentGatewayServie
				.makePayment(
						new MpesaRequestDTO(context.getUsername(), subscriptionDetails.getWalletNumber(),
								subscriptionDetails.getTotal().toPlainString(),
								this.mpesa.getThirtPartyReference(),
								this.mpesa.getProviderCode()));

		if ("INS-10".equals(mpesaResponseDTO.getOutput_ResponseCode())) {
			throw new BusinessException(this.translator.getTranslation("duplicated.transaction"));
		}

		subscriptionDetails.setStatus(mpesaResponseDTO.getOutput_ResponseCode());
		subscriptionDetails.setStatusDescription(mpesaResponseDTO.getOutput_ResponseDesc());

	}

	@Override
	public Boolean wasPaymentCompleted(final SubscriptionDetails subscriptionDetails) throws BusinessException {
		return MpesaPaymentService.MPESA_SUCCESS.equals(subscriptionDetails.getStatus());
	}
}
