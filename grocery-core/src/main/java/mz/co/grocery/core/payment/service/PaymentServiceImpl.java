/**
 *
 */
package mz.co.grocery.core.payment.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.dao.GroceryDAO;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.payment.model.Payment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(PaymentServiceImpl.NAME)
public class PaymentServiceImpl extends AbstractService implements PaymentService {

	public static final String NAME = "mz.co.grocery.core.payment.service.PaymentServiceImpl";

	private static final String MPESA_SUCCESS = "INS-0";

	private static final BigDecimal DEFAULT_DEBIT = new BigDecimal(1);

	@Inject
	private GroceryDAO unitDAO;

	@Override
	public Payment updateSubscription(final UserContext userContext, final Payment payment) throws BusinessException {

		if (!PaymentServiceImpl.MPESA_SUCCESS.equals(payment.getStatus())) {
			throw new BusinessException(payment.getStatusDescription());
		}

		final Grocery unit = this.unitDAO.findByUuid(payment.getUnitUuid());

		unit.setBalance(payment.getVoucherValue());
		this.unitDAO.update(userContext, unit);

		return payment;
	}

	@Override
	public void debitTransaction(final UserContext userContext, final String unitUuid) throws BusinessException {

		final Grocery unit = this.unitDAO.findByUuid(unitUuid);

		if (PaymentServiceImpl.DEFAULT_DEBIT.doubleValue() > unit.getBalance().doubleValue()) {
			throw new BusinessException("Insufficient funds");
		}

		unit.debitTransaction(PaymentServiceImpl.DEFAULT_DEBIT);
		this.unitDAO.update(userContext, unit);
	}
}
