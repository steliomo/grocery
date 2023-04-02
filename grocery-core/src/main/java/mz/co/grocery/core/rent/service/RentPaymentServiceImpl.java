/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.model.ReturnStatus;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.core.util.BigDecimalUtil;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@Service(RentPaymentServiceImpl.NAME)
public class RentPaymentServiceImpl extends AbstractService implements RentPaymentService {

	public static final String NAME = "mz.co.grocery.core.rent.service.RentPaymentServiceImpl";

	@Inject
	private RentPaymentDAO rentPaymentDAO;

	@Inject
	private RentDAO rentDAO;

	@Inject
	private PaymentService paymentService;

	@Inject
	private ApplicationTranslator translator;

	@Override
	public RentPayment makeRentPayment(final UserContext userContext, final RentPayment rentPayment) throws BusinessException {

		if (BigDecimalUtil.isZero(rentPayment.getPaymentValue())) {
			throw new BusinessException(this.translator.getTranslation("rent.payment.value.cannot.be.zero"));
		}

		final Rent rent = this.rentDAO.findByUuid(rentPayment.getRent().getUuid());

		final BigDecimal totalToRefund = rent.totalToRefund();

		if (BigDecimalUtil.isZero(totalToRefund)) {

			this.rentPaymentDAO.create(userContext, rentPayment);

			rent.setTotalPaid(rentPayment.getPaymentValue());
			rent.setPaymentStatus();
			this.rentDAO.update(userContext, rent);

			this.paymentService.debitTransaction(userContext, rent.getUnit().getUuid());
			return rentPayment;
		}

		if (!ReturnStatus.COMPLETE.equals(rent.getReturnStatus())) {
			throw new BusinessException(this.translator.getTranslation("rent.payment.refund.cannot.be.performed"));
		}

		if (BigDecimalUtil.isGraterThan(rentPayment.getPaymentValue(), totalToRefund)) {
			throw new BusinessException(this.translator.getTranslation("rent.payment.refund.value.is.unexpected"));
		}

		this.rentPaymentDAO.create(userContext, rentPayment);

		rent.refund(rentPayment.getPaymentValue());
		rent.setPaymentStatus();
		this.rentDAO.update(userContext, rent);

		this.paymentService.debitTransaction(userContext, rent.getUnit().getUuid());
		return rentPayment;
	}
}
