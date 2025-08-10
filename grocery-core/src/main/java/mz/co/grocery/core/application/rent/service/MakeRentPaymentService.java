/**
 *
 */
package mz.co.grocery.core.application.rent.service;

import java.math.BigDecimal;

import mz.co.grocery.core.application.rent.in.MakeRentPaymentUseCase;
import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.rent.ReturnStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class MakeRentPaymentService extends AbstractService implements MakeRentPaymentUseCase {

	private RentPort rentPort;

	private RentPaymentPort rentPaymentPort;

	public MakeRentPaymentService(final RentPort rentPort, final RentPaymentPort rentPaymentPort) {
		this.rentPort = rentPort;
		this.rentPaymentPort = rentPaymentPort;
	}

	@Override
	public RentPayment makeRentPayment(final UserContext userContext, final RentPayment rentPayment) throws BusinessException {

		if (BigDecimalUtil.isZero(rentPayment.getPaymentValue())) {
			throw new BusinessException("rent.payment.value.cannot.be.zero");
		}

		final Rent rent = this.rentPort.findByUuid(rentPayment.getRent().get().getUuid());

		final BigDecimal totalToRefund = rent.totalToRefund();

		if (BigDecimalUtil.isZero(totalToRefund)) {

			this.rentPaymentPort.createRentPayment(userContext, rentPayment);

			rent.setTotalPaid(rentPayment.getPaymentValue());
			rent.setPaymentStatus();
			rent.closeRentStatus();
			this.rentPort.updateRent(userContext, rent);

			return rentPayment;
		}

		if (!ReturnStatus.COMPLETE.equals(rent.getReturnStatus())) {
			throw new BusinessException("rent.payment.refund.cannot.be.performed");
		}

		if (BigDecimalUtil.isGraterThan(rentPayment.getPaymentValue(), totalToRefund)) {
			throw new BusinessException("rent.payment.refund.value.is.unexpected");
		}

		this.rentPaymentPort.createRentPayment(userContext, rentPayment);

		rent.refund(rentPayment.getPaymentValue());
		rent.setPaymentStatus();
		rent.closeRentStatus();
		this.rentPort.updateRent(userContext, rent);

		return rentPayment;
	}
}
