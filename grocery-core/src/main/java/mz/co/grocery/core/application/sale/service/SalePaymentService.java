/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.sale.in.SalePaymentUseCase;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class SalePaymentService extends AbstractService implements SalePaymentUseCase {

	private SalePort salePort;

	private SalePaymentPort salePaymentPort;

	private PaymentUseCase paymentUseCase;

	public SalePaymentService(final SalePort salePort, final SalePaymentPort salePaymentPort, final PaymentUseCase paymentUseCase) {
		this.salePort = salePort;
		this.salePaymentPort = salePaymentPort;
		this.paymentUseCase = paymentUseCase;
	}

	@Override
	public SalePayment payInstallmentSale(final UserContext userContext, final String saleUuid, final BigDecimal paymentValue,
			final LocalDate paymentDate)
					throws BusinessException {

		final Sale sale = this.salePort.fetchByUuid(saleUuid);

		if (!SaleType.INSTALLMENT.equals(sale.getSaleType())) {
			throw new BusinessException("cannot.perform.payment.for.non.installment.sale");
		}

		if (paymentValue.compareTo(sale.getRemainingPayment()) == BigDecimal.ONE.intValue()) {
			throw new BusinessException("the.payment.value.is.higher.than.expected");
		}

		sale.updateTotalPaid(paymentValue);
		sale.updateSaleStatus();
		this.salePort.updateSale(userContext, sale);

		final SalePayment salePayment = new SalePayment(sale, paymentValue, paymentDate);

		this.salePaymentPort.createSalePayment(userContext, salePayment);

		if (SaleStatus.CLOSED.equals(sale.getSaleStatus())) {
			this.paymentUseCase.debitTransaction(userContext, sale.getUnit().get().getUuid());
		}

		return salePayment;
	}
}
