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
import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.out.GuideItemPort;
import mz.co.grocery.core.application.guide.out.GuidePort;
import mz.co.grocery.core.application.guide.service.IssueGuideService;
import mz.co.grocery.core.application.guide.service.ReturnGuideIssuer;
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
import mz.co.grocery.core.fixturefactory.GuideItemTemplate;
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
public class ReturnGuideTest extends AbstractUnitServiceTest {

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private DocumentGeneratorPort reportGeneratorPort;

	@Mock
	private SubscriptionUseCase paymentUseCase;

	@Mock
	private GuidePort guidePort;

	@Mock
	private GuideItemPort guideItemPort;

	@Mock
	private RentItemPort rentItemPort;

	@Mock
	private StockPort stockPort;

	@Mock
	private RentPort rentPort;

	@InjectMocks
	private IssueGuideService guideService;

	@InjectMocks
	private final GuideIssuer returnGuideIssuer = new ReturnGuideIssuer(this.guidePort, this.guideItemPort, this.rentItemPort, this.stockPort,
			this.rentPort);

	@Test
	public void shouldIssueReturnGuideForProducts() throws BusinessException {

		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final LocalDate rentDate = LocalDate.now().minusDays(10);
		final Guide guide = new GuideUnitBuider(GuideTemplate.RETURN).withRentProducts(1).build();

		final UserContext context = this.getUserContext();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				final RentItem ri = (RentItem) result;
				ri.setPlannedQuantity(new BigDecimal(100));
				ri.addLoadedQuantity(new BigDecimal(90));

				ri.getRentalChunkValueOnLoading(rentDate);
				ri.setStockable();
				ri.setLoadingDate(rentDate);
				ri.setLoadStatus();
			}
		});

		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);

		Mockito.when(this.guideItemPort.createGuideItem(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class)))
		.thenReturn(guide.getGuideItems().get().stream().findFirst().get());

		Mockito.when(this.rentItemPort.findByUuid(ArgumentMatchers.any()))
		.thenAnswer(invocation -> rentItem);

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		Mockito.when(this.stockPort.findStockByUuid(ArgumentMatchers.any())).thenReturn(stock);

		Mockito.when(this.rentPort.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent().get());

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemPort, Mockito.times(1)).createGuideItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemPort, Mockito.times(1)).updateRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockPort, Mockito.times(1)).updateStock(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentPort, Mockito.times(1)).updateRent(context, guide.getRent().get());
		Mockito.verify(this.guidePort, Mockito.times(1)).createGuide(context, guide);

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.RETURN, guide.getType());
		Assert.assertEquals(LoadStatus.COMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.INCOMPLETE, rentItem.getReturnStatus());
	}

	@Test
	public void shouldIssueReturnGuideForServices() throws BusinessException {

		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final LocalDate rentDate = LocalDate.now().minusDays(10);
		final Guide guide = new GuideUnitBuider(GuideTemplate.RETURN).withRentProducts(1).build();

		final UserContext context = this.getUserContext();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.SERVICE, result -> {
			if (result instanceof RentItem) {
				final RentItem ri = (RentItem) result;
				ri.setPlannedQuantity(new BigDecimal(100));
				ri.addLoadedQuantity(new BigDecimal(90));

				ri.getRentalChunkValueOnLoading(rentDate);
				ri.setStockable();
				ri.setLoadingDate(rentDate);
				ri.setLoadStatus();
			}
		});

		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);

		Mockito.when(this.guideItemPort.createGuideItem(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class)))
		.thenReturn(guide.getGuideItems().get().stream().findFirst().get());

		Mockito.when(this.rentItemPort.findByUuid(ArgumentMatchers.any()))
		.thenAnswer(invocation -> rentItem);

		Mockito.when(this.rentPort.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent().get());

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemPort, Mockito.times(1)).createGuideItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemPort, Mockito.times(1)).updateRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockPort, Mockito.times(0)).updateStock(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentPort, Mockito.times(1)).updateRent(context, guide.getRent().get());
		Mockito.verify(this.guidePort, Mockito.times(1)).createGuide(context, guide);

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.RETURN, guide.getType());
		Assert.assertEquals(LoadStatus.COMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.INCOMPLETE, rentItem.getReturnStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldIssueReturnGuideForEmptyItems() throws BusinessException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.NO_ITEMS_RETURN).build();

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test
	public void shouldRecalculateTotalEstimatedWhenIssueReturnGuideInTheLoadDate() throws BusinessException {

		this.guideService.setGuideIssuer(this.returnGuideIssuer);

		final LocalDate rentDate = LocalDate.now();
		final Guide guide = new GuideUnitBuider(GuideTemplate.RETURN).build();
		final GuideItem guideItem = EntityFactory.gimme(GuideItem.class, GuideItemTemplate.RENT_PRODUCTS);
		guideItem.setQuantity(new BigDecimal(10));
		guide.addGuideItem(guideItem);

		final UserContext context = this.getUserContext();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				final RentItem ri = (RentItem) result;
				ri.setPlannedQuantity(new BigDecimal(100));
				ri.addLoadedQuantity(new BigDecimal(100));

				ri.getRentalChunkValueOnLoading(rentDate);
				ri.calculatePlannedTotal();
				ri.setStockable();
				ri.setLoadingDate(rentDate);
				ri.setLoadStatus();
			}
		});

		Mockito.when(this.guidePort.createGuide(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Guide.class))).thenReturn(guide);

		Mockito.when(this.guideItemPort.createGuideItem(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class)))
		.thenReturn(guide.getGuideItems().get().stream().findFirst().get());

		Mockito.when(this.rentItemPort.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		Mockito.when(this.stockPort.findStockByUuid(ArgumentMatchers.any())).thenReturn(stock);

		Mockito.when(this.rentPort.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent().get());

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemPort, Mockito.times(1)).createGuideItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemPort, Mockito.times(1)).updateRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockPort, Mockito.times(1)).updateStock(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentPort, Mockito.times(1)).updateRent(context, guide.getRent().get());
		Mockito.verify(this.guidePort, Mockito.times(1)).createGuide(context, guide);

		Assert.assertEquals(rentDate, guide.getIssueDate());
		Assert.assertEquals(GuideType.RETURN, guide.getType());
	}
}
