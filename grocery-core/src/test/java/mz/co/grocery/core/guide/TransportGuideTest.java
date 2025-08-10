/**
 *
 */
package mz.co.grocery.core.guide;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.guide.out.GuideItemPort;
import mz.co.grocery.core.application.guide.out.GuidePort;
import mz.co.grocery.core.application.guide.service.IssueGuideService;
import mz.co.grocery.core.application.guide.service.TransportGuideIssuer;
import mz.co.grocery.core.application.payment.in.SubscriptionUseCase;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.LoadStatus;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.ReturnStatus;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.guide.builder.GuideUnitBuider;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class TransportGuideTest extends AbstractUnitServiceTest {

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private DocumentGeneratorPort reportGeneratorPort;

	@Mock
	private SubscriptionUseCase paymentUseCase;

	@Mock
	private GuidePort guidePort;

	@Mock
	private RentPort rentPort;

	@Mock
	private GuideItemPort guideItemPort;

	@Mock
	private RentItemPort rentItemPort;

	@Mock
	private StockPort stockPort;

	@InjectMocks
	private IssueGuideService guideService;

	@InjectMocks
	private TransportGuideIssuer transportGuideIssuer;

	@Test
	public void shouldIssueTransportGuideForProducts() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withRentProducts(2).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		guide.setIssueDate(LocalDate.now());
		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);

		Mockito.when(this.guideItemPort.createGuideItem(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class)))
		.thenReturn(guide.getGuideItems().get().stream().findFirst().get());

		Mockito.when(this.rentItemPort.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.stockPort.findStockByUuid(ArgumentMatchers.any())).thenReturn(EntityFactory.gimme(Stock.class,
				StockTemplate.VALID));

		Mockito.when(this.rentPort.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent().get());

		final UserContext context = this.getUserContext();

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemPort, Mockito.times(2)).createGuideItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemPort, Mockito.times(2)).updateRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockPort, Mockito.times(2)).updateStock(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentPort, Mockito.times(1)).updateRent(context, guide.getRent().get());
		Mockito.verify(this.guidePort, Mockito.times(1)).createGuide(context, guide);

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.TRANSPORT, guide.getType());
		Assert.assertEquals(BigDecimal.ZERO, guide.getRent().get().getTotalCalculated());
		Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.PENDING, rentItem.getReturnStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldIssueTransportGuideToLoadMoreThanPlanned() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withRentProducts(20).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		guide.setIssueDate(LocalDate.now());
		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);

		Mockito.when(this.guideItemPort.createGuideItem(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class)))
		.thenReturn(guide.getGuideItems().get().stream().findFirst().get());

		Mockito.when(this.rentItemPort.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.stockPort.findStockByUuid(ArgumentMatchers.any())).thenReturn(EntityFactory.gimme(Stock.class,
				StockTemplate.VALID));

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test
	public void shouldIssueTransportGuideForServices() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withRentProducts(2).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.SERVICE, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		guide.setIssueDate(LocalDate.now());
		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);

		Mockito.when(this.guideItemPort.createGuideItem(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class)))
		.thenReturn(guide.getGuideItems().get().stream().findFirst().get());

		Mockito.when(this.rentItemPort.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.rentPort.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent().get());

		final UserContext context = this.getUserContext();

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemPort, Mockito.times(2)).createGuideItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemPort, Mockito.times(2)).updateRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockPort, Mockito.times(0)).updateStock(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentPort, Mockito.times(1)).updateRent(context, guide.getRent().get());
		Mockito.verify(this.guidePort, Mockito.times(1)).createGuide(context, guide);

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.TRANSPORT, guide.getType());
		Assert.assertEquals(BigDecimal.ZERO.doubleValue(), guide.getRent().get().getTotalCalculated().doubleValue(), 0.0);
		Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.PENDING, rentItem.getReturnStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueTransportGuidesForEmptyItems() throws BusinessException {
		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_TRANSPORT);

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

}
