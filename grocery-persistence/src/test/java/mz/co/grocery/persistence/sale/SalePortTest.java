/**
 *
 */
package mz.co.grocery.persistence.sale;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.DeliveryGuideIssuer;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SalePortTest extends AbstractIntegServiceTest {

	@Inject
	private SalePort salePort;

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private IssueGuideUseCase guideService;

	@Inject
	@BeanQualifier(DeliveryGuideIssuer.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Inject
	private SaleItemPort saleItemPort;

	private Sale sale;

	@Before
	public void before() throws BusinessException {
		this.sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.CASH).build();
	}

	@Test
	public void shouldFindSalePerPeriod() throws BusinessException {
		final List<SaleReport> sales = this.salePort.findSalesByUnitAndPeriod(this.sale.getUnit().get().getUuid(), LocalDate.now(),
				LocalDate.now());
		Assert.assertFalse(sales.isEmpty());
	}

	@Test
	public void shouldFindMonthlySalePerPeriod() throws BusinessException {

		final List<SaleReport> sales = this.salePort.findSalesByUnitAndMonthlyPeriod(this.sale.getUnit().get().getUuid(),
				LocalDate.now(),
				LocalDate.now());

		Assert.assertFalse(sales.isEmpty());
		Assert.assertEquals(1, sales.size());
	}

	@Test
	public void shouldFindPendingOrIncompleteSalesStatusByCustomer() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.INSTALLMENT).withCustomer()
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Sale> sales = this.salePort.findPendingOrImpletePaymentSaleStatusByCustomer(sale.getCustomer().get());

		Assert.assertFalse(sales.isEmpty());
	}

	@Test
	public void shouldFetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.INSTALLMENT).withCustomer()
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Sale> sales = this.salePort.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(sale.getCustomer().get());

		Assert.assertFalse(sales.isEmpty());

		sales.forEach(s -> {
			Assert.assertFalse(s.getItems().get().isEmpty());
		});
	}

	@Test
	public void shouldFetchSalesWithDeliveryGuidesByCustomer() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now())
				.build();

		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);

		guide.setSale(sale);

		for (SaleItem saleItem : sale.getItems().get()) {
			saleItem.setSale(sale);

			saleItem = this.saleItemPort.createSaleItem(this.getUserContext(), saleItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(saleItem.getQuantity());
			guideItem.setSaleItem(saleItem);
			guide.addGuideItem(guideItem);
		}

		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Sale> sales = this.salePort.fetchSalesWithDeliveryGuidesByCustomer(sale.getCustomer().get());

		Assert.assertFalse(sales.isEmpty());
	}
}
