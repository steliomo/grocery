/**
 *
 */
package mz.co.grocery.core.sale.integ;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.core.sale.service.SaleQueryService;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class SaleQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private SaleQueryService saleQueryService;

	@Mock
	private ApplicationTranslator translator;

	@Inject
	private SaleBuilder saleBuilder;

	private Sale sale;

	@Before
	public void before() throws BusinessException {
		this.sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.CASH).build();
		this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.INSTALLMENT).withCustomer()
		.dueDate(LocalDate.now().plusDays(30)).build();
	}

	@Test
	public void shouldFindSalePerPeriod() throws BusinessException {
		final List<SaleReport> sales = this.saleQueryService.findSalesPerPeriod(this.sale.getGrocery().getUuid(), LocalDate.now(),
				LocalDate.now());
		Assert.assertFalse(sales.isEmpty());
	}

	@Test
	public void shouldFindMonthlySalePerPeriod() throws BusinessException {

		final List<SaleReport> sales = this.saleQueryService.findMonthlySalesPerPeriod(this.sale.getGrocery().getUuid(),
				LocalDate.now(),
				LocalDate.now());

		Assert.assertFalse(sales.isEmpty());
		Assert.assertEquals(1, sales.size());
	}

	@Test
	public void shouldFindPendingOrIncompleteSalesStatusByCustomer() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.INSTALLMENT).withCustomer()
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Sale> sales = this.saleQueryService.findPendingOrImpletePaymentSaleStatusByCustomer(sale.getCustomer());

		Assert.assertFalse(sales.isEmpty());
		sales.forEach(s -> {
			Assert.assertEquals(sale.getCustomer().getId(), s.getCustomer().getId());
		});
	}
}
