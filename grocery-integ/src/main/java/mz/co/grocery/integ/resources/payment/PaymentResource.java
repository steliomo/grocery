/**
 *
 */
package mz.co.grocery.integ.resources.payment;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.scheduling.annotation.Scheduled;

import mz.co.grocery.core.application.payment.in.SubscriptionRenewalNotificationUseCase;
import mz.co.grocery.core.application.payment.in.SubscriptionUseCase;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.payment.SubscriptionDetails;
import mz.co.grocery.core.domain.payment.Voucher;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.common.EnumsDTO;
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
	private SubscriptionUseCase paymentService;

	@Inject
	private SubscriptionRenewalNotificationUseCase subscriptionRenewalNotificationUseCase;

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
		final SubscriptionDetails payment = new SubscriptionDetails(voucher);
		return Response.ok(payment).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makePayment(final SubscriptionDetails subscriptionDetails) throws BusinessException {

		this.paymentService.updateSubscription(this.getContext(), subscriptionDetails);

		return Response.ok(subscriptionDetails).build();
	}


	@Scheduled(cron = "10 30 0 * * ?")
	public void sendSalesDailyReport() throws BusinessException {
		final LocalDate notificationDate = LocalDate.now();

		this.subscriptionRenewalNotificationUseCase.sendNotification(notificationDate);
	}
}
