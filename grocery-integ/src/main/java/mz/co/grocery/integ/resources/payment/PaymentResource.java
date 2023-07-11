/**
 *
 */
package mz.co.grocery.integ.resources.payment;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.payment.Payment;
import mz.co.grocery.core.domain.payment.Voucher;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.common.EnumsDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaRequestDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaResponseDTO;
import mz.co.grocery.integ.resources.payment.service.MpesaPaymentGatewayServieImpl;
import mz.co.grocery.integ.resources.payment.service.PaymentGatewayService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("payments")
@WebAdapter
public class PaymentResource extends AbstractResource {

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private PaymentUseCase paymentService;

	@Inject
	private Mpesa mpesa;

	@GET
	@Path("vouchers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentTypes() {
		final EnumsDTO<Voucher> paymentTypes = new EnumsDTO<>(this.translator, Voucher.values());
		return Response.ok(paymentTypes).build();
	}

	@GET
	@Path("calculate-payment/{voucher}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response calculatePayment(@PathParam("voucher") final Voucher voucher) {
		final Payment payment = new Payment(voucher);
		return Response.ok(payment).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makePayment(final Payment payment) throws BusinessException {

		final PaymentGatewayService<MpesaRequestDTO, MpesaResponseDTO> paymentGatewayServie = new MpesaPaymentGatewayServieImpl(this.mpesa);
		final MpesaResponseDTO mpesaResponseDTO = paymentGatewayServie
				.makePayment(
						new MpesaRequestDTO(this.getContext().getUsername(), payment.getMpesaNumber(), payment.getTotal().toPlainString(),
								this.mpesa.getThirtPartyReference(),
								this.mpesa.getProviderCode()));

		if ("INS-10".equals(mpesaResponseDTO.getOutput_ResponseCode())) {
			throw new BusinessException(this.translator.getTranslation("duplicated.transaction"));
		}

		payment.setStatus(mpesaResponseDTO.getOutput_ResponseCode());
		payment.setStatusDescription(mpesaResponseDTO.getOutput_ResponseDesc());

		this.paymentService.updateSubscription(this.getContext(), payment);

		return Response.ok(payment).build();
	}
}
