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
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.service.DeliveryGuideIssuerImpl;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.sale.builder.SaleBuilder;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.core.sale.service.SaleQueryService;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

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

	@Inject
	private GuideService guideService;

	@Inject
	@Qualifier(DeliveryGuideIssuerImpl.NAME)
	private GuideIssuer deliveryGuideIssuer;

	private Sale sale;

	@Before
	public void before() throws BusinessException {
		this.sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.CASH).build();
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

	@Test
	public void shouldFetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withUnit().withProducts(10).withServices(10).saleType(SaleType.INSTALLMENT).withCustomer()
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Sale> sales = this.saleQueryService.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(sale.getCustomer());

		Assert.assertFalse(sales.isEmpty());

		sales.forEach(s -> {
			Assert.assertFalse(s.getItems().isEmpty());
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

		sale.getItems().forEach(saleItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(saleItem.getQuantity());
			guideItem.setSaleItem(saleItem);
			guide.addGuideItem(guideItem);
		});

		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Sale> sales = this.saleQueryService.fetchSalesWithDeliveryGuidesByCustomer(sale.getCustomer());

		Assert.assertFalse(sales.isEmpty());

		sales.forEach(s -> {
			Assert.assertFalse(s.getGuides().isEmpty());
			s.getGuides().forEach(g -> {
				Assert.assertFalse(g.getGuideItems().isEmpty());
			});
		});
	}
}
