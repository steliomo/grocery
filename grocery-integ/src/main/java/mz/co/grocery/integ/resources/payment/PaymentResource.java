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

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import mz.co.grocery.core.payment.model.Payment;
import mz.co.grocery.core.payment.model.Voucher;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.payment.dto.MpesaRequestDTO;
import mz.co.grocery.integ.resources.payment.dto.MpesaResponseDTO;
import mz.co.grocery.integ.resources.payment.service.MpesaPaymentGatewayServieImpl;
import mz.co.grocery.integ.resources.payment.service.PaymentGatewayService;
import mz.co.grocery.integ.resources.util.EnumsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("payments")
@Service(PaymentResource.NAME)
public class PaymentResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.payment.PaymentResource";

	@Inject
	private MessageSource messageSource;

	@Inject
	private PaymentService paymentService;

	@Inject
	private Mpesa mpesa;

	@GET
	@Path("vouchers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentTypes() {
		final EnumsDTO<Voucher> paymentTypes = new EnumsDTO<>(this.messageSource, Voucher.values());
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

		payment.setStatus(mpesaResponseDTO.getOutput_ResponseCode());
		payment.setStatusDescription(mpesaResponseDTO.getOutput_ResponseDesc());

		this.paymentService.updateSubscription(this.getContext(), payment);

		return Response.ok(payment).build();
	}
}
