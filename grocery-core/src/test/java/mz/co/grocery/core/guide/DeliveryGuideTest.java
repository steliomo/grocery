/**
 *
 */
package mz.co.grocery.core.guide;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.out.GuideItemPort;
import mz.co.grocery.core.application.guide.out.GuidePort;
import mz.co.grocery.core.application.guide.service.DeliveryGuideIssuer;
import mz.co.grocery.core.application.guide.service.IssueGuideService;
import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideItemType;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.GuideItemTemplate;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.guide.builder.GuideUnitBuider;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class DeliveryGuideTest extends AbstractUnitServiceTest {

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private DocumentGeneratorPort reportGeneratorPort;

	@Mock
	private PaymentUseCase paymentUseCase;

	@Mock
	private GuidePort guidePort;

	@Mock
	private GuideItemPort guideItemPort;

	@Mock
	private StockPort stockPort;

	@Mock
	private SaleItemPort saleItemPort;

	@Mock
	private SalePort salePort;

	@InjectMocks
	private final IssueGuideUseCase guideService = new IssueGuideService(this.translator, this.reportGeneratorPort, this.paymentUseCase);

	@InjectMocks
	private final GuideIssuer deliveryGuideIssuer = new DeliveryGuideIssuer(this.guidePort, this.guideItemPort, this.saleItemPort, this.stockPort,
			this.salePort);

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideForNonDeliveryGuideType() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.TRANSPORT);

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideForNonInstallmentSales() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);
		guide.getSale().get().setSaleType(SaleType.CASH);

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideWithoutGuideItems() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);
		guide.getSale().get().setSaleType(SaleType.INSTALLMENT);

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideForGuideItemsWithQuantityAboveGreaterThanToDeliver() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);
		guide.getSale().get().setSaleType(SaleType.INSTALLMENT);
		final GuideItem guideItem = EntityFactory.gimme(GuideItem.class, GuideItemTemplate.SALE_PRODUCTS);
		guideItem.setQuantity(new BigDecimal(500));
		guide.addGuideItem(guideItem);

		final SaleItem saleItem = guideItem.getSaleItem().get();
		saleItem.setDeliveredQuantity(BigDecimal.ZERO);

		Mockito.when(this.saleItemPort.findByUuid(ArgumentMatchers.any())).thenReturn(saleItem);

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test
	public void shouldIssueDeliveryGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideType.DELIVERY.toString()).withSaleProducts(3).build();
		guide.getSale().get().setSaleType(SaleType.INSTALLMENT);
		guide.getSale().get().setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		final SaleItem saleItem = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.PRODUCT);
		guide.getSale().get().addItem(saleItem);
		saleItem.setDeliveredQuantity(BigDecimal.ZERO);

		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);
		Mockito.when(this.saleItemPort.findByUuid(ArgumentMatchers.any())).thenReturn(saleItem);
		Mockito.when(this.stockPort.findStockByUuid(ArgumentMatchers.any())).thenReturn(saleItem.getStock().get());
		Mockito.when(this.salePort.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getSale().get());

		this.guideService.issueGuide(this.getUserContext(), guide);

		Mockito.verify(this.guidePort, Mockito.times(1)).createGuide(this.getUserContext(), guide);
		Mockito.verify(this.guideItemPort, Mockito.times(3)).createGuideItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.saleItemPort, Mockito.times(3)).updateSaleItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(SaleItem.class));
		Mockito.verify(this.paymentUseCase, Mockito.times(1)).debitTransaction(this.getUserContext(), guide.getUnit().getUuid());
		Mockito.verify(this.salePort, Mockito.times(1)).fetchByUuid(ArgumentMatchers.any());
		Mockito.verify(this.salePort, Mockito.times(1)).updateSale(ArgumentMatchers.any(), ArgumentMatchers.any());

		Assert.assertEquals(GuideType.DELIVERY, guide.getType());

		Assert.assertNotNull(guide);
		Assert.assertEquals(DeliveryStatus.INCOMPLETE, guide.getSale().get().getDeliveryStatus());

		guide.getGuideItems().get().forEach(guideItem -> {
			Assert.assertEquals(GuideItemType.SALE, guideItem.getItemGuideType());
		});
	}
}
