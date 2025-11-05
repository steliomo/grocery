/**
 *
 */
package mz.co.grocery.persistence.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.pos.in.PayDeptUseCase;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class SalePaymentPortTest extends AbstractIntegServiceTest {

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private PayDeptUseCase payDeptUseCase;

	@Inject
	private SalePaymentPort salePaymentPort;

	@Test
	public void shoulFindDebtCollectionsByUnitAndPeriod() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.CREDIT)
				.withTotal(new BigDecimal(1000)).withTotalPaid(new BigDecimal(1000)).dueDate(LocalDate.now()).build();

		final LocalDate startDate = LocalDate.now();
		final LocalDate endDate = LocalDate.now();

		final Debt debt = new Debt();
		debt.setCustomer(sale.getCustomer().get());
		debt.setAmount(new BigDecimal(1000));

		this.payDeptUseCase.pay(this.getUserContext(), debt);

		final Optional<BigDecimal> debtCollections = this.salePaymentPort.findDebtCollectionsByUnitAndPeriod(sale.getUnit().get().getUuid(),
				startDate,
				endDate);

		Assert.assertTrue(debtCollections.isPresent());
		Assert.assertTrue(BigDecimalUtil.isEqual(debt.getAmount(), debtCollections.get()));
	}

}
