/**
 *
 */
package mz.co.grocery.core.application.pos.service;

import java.math.BigDecimal;
import java.util.List;

import mz.co.grocery.core.application.pos.in.PayDeptUseCase;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class PayDeptService extends AbstractService implements PayDeptUseCase {

	private SalePort salePort;

	private SalePaymentPort salePaymentPort;

	private Clock clock;

	public PayDeptService(final SalePort salePort, final SalePaymentPort salePaymentPort, final Clock clock) {
		this.salePort = salePort;
		this.salePaymentPort = salePaymentPort;
		this.clock = clock;
	}

	@Override
	public Debt pay(final UserContext userContext, final Debt dept) throws BusinessException {

		if (!dept.getCustomer().isPresent()) {
			throw new BusinessException("pos.pay.dept.must.have.customer");
		}

		final List<Sale> sales = this.salePort.findCreditSaleTypeAndPendingSaleStatusSalesByCustomer(dept.getCustomer().get());

		BigDecimal amountPaying = dept.getAmount();

		for (final Sale sale : sales) {

			if (BigDecimalUtil.isGraterThanOrEqual(sale.getRemainingPayment(), amountPaying)) {
				sale.updateTotalPaid(amountPaying);
				sale.updateSaleStatus();

				this.salePort.updateSale(userContext, sale);

				this.salePaymentPort.createSalePayment(userContext, new SalePayment(sale, amountPaying, this.clock.todayDate()));

				break;
			}

			final BigDecimal toPay = sale.getRemainingPayment();

			sale.updateTotalPaid(toPay);
			sale.updateSaleStatus();

			this.salePort.updateSale(userContext, sale);

			this.salePaymentPort.createSalePayment(userContext, new SalePayment(sale, toPay, this.clock.todayDate()));

			amountPaying = amountPaying.subtract(toPay);
		}

		return dept;
	}
}
