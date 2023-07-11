/**
 *
 */
package mz.co.grocery.core.application.payment.service;

import java.math.BigDecimal;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.payment.Payment;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class PaymentService extends AbstractService implements PaymentUseCase {

	public static final String NAME = "mz.co.grocery.core.payment.service.PaymentServiceImpl";

	private static final String MPESA_SUCCESS = "INS-0";

	private static final BigDecimal DEFAULT_DEBIT = BigDecimal.ONE;

	private UnitPort unitPort;

	public PaymentService(final UnitPort unitPort) {
		this.unitPort = unitPort;
	}

	@Override
	public Payment updateSubscription(final UserContext userContext, final Payment payment) throws BusinessException {

		if (!PaymentService.MPESA_SUCCESS.equals(payment.getStatus())) {
			throw new BusinessException(payment.getStatusDescription());
		}

		final Unit unit = this.unitPort.findByUuid(payment.getUnitUuid());

		unit.setBalance(payment.getVoucherValue());
		this.unitPort.updateUnit(userContext, unit);

		return payment;
	}

	@Override
	public void debitTransaction(final UserContext userContext, final String unitUuid) throws BusinessException {

		final Unit unit = this.unitPort.findByUuid(unitUuid);

		if (PaymentService.DEFAULT_DEBIT.compareTo(unit.getBalance()) == BigDecimal.ONE.intValue()) {
			throw new BusinessException("no.funds.available");
		}

		unit.debitTransaction(PaymentService.DEFAULT_DEBIT);
		this.unitPort.updateUnit(userContext, unit);
	}
}
