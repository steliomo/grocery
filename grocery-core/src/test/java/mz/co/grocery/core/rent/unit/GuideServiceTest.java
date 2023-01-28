/**
 *
 */
package mz.co.grocery.core.rent.unit;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.builder.GuideUnitBuider;
import mz.co.grocery.core.rent.dao.GuideDAO;
import mz.co.grocery.core.rent.dao.GuideItemDAO;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideItem;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.core.rent.model.LoadStatus;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.ReturnStatus;
import mz.co.grocery.core.rent.service.GuideIssuer;
import mz.co.grocery.core.rent.service.GuideService;
import mz.co.grocery.core.rent.service.GuideServiceImpl;
import mz.co.grocery.core.rent.service.ReturnGuideIssuer;
import mz.co.grocery.core.rent.service.TransportGuideIssuer;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class GuideServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final GuideService guideService = new GuideServiceImpl();

	@InjectMocks
	private final GuideIssuer transportGuideIssuer = new TransportGuideIssuer();

	@InjectMocks
	private final GuideIssuer returnGuideIssuer = new ReturnGuideIssuer();

	@Mock
	private RentDAO rentDAO;

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private PaymentService paymentService;

	@Mock
	private GuideDAO guideDAO;

	@Mock
	private GuideItemDAO guideItemDAO;

	@Mock
	private RentItemDAO rentItemDAO;

	@Mock
	private StockDAO stockDAO;

	@Test
	public void shouldIssueTransportGuideForProducts() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withProducts(2).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.stockDAO.findByUuid(ArgumentMatchers.any())).thenReturn(EntityFactory.gimme(Stock.class,
				StockTemplate.VALID));

		Mockito.when(this.rentDAO.findByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent());

		final UserContext context = this.getUserContext();

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemDAO, Mockito.times(2)).create(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemDAO, Mockito.times(2)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockDAO, Mockito.times(2)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentDAO, Mockito.times(1)).update(context, guide.getRent());
		Mockito.verify(this.guideDAO, Mockito.times(1)).create(context, guide);
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(context, guide.getUnit().getUuid());

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.TRANSPORT, guide.getType());
		Assert.assertEquals(BigDecimal.ZERO, guide.getRent().getTotalCalculated());
		Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.PENDING, rentItem.getReturnStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldIssueTransportGuideToLoadMoreThanPlanned() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withProducts(20).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.stockDAO.findByUuid(ArgumentMatchers.any())).thenReturn(EntityFactory.gimme(Stock.class,
				StockTemplate.VALID));

		Mockito.when(this.translator.getTranslation("guide.unexpected.item.load.quantity", new String[] { rentItem.getItem().getName() }))
		.thenReturn("Unexpected quantilty to load for " + rentItem.getItem().getName());

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test
	public void shouldIssueTransportGuideForServices() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withProducts(2).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.SERVICE, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.rentDAO.findByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent());

		final UserContext context = this.getUserContext();

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemDAO, Mockito.times(2)).create(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemDAO, Mockito.times(2)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockDAO, Mockito.times(0)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentDAO, Mockito.times(1)).update(context, guide.getRent());
		Mockito.verify(this.guideDAO, Mockito.times(1)).create(context, guide);
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(context, guide.getUnit().getUuid());

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.TRANSPORT, guide.getType());
		Assert.assertEquals(BigDecimal.ZERO.doubleValue(), guide.getRent().getTotalCalculated().doubleValue(), 0.0);
		Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.PENDING, rentItem.getReturnStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueTransportGuidesForEmptyItems() throws BusinessException {
		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_TRANSPORT);

		Mockito.when(this.translator.getTranslation("guide.must.have.items")).thenReturn("Cannot issue transport guide without items");

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test
	public void shouldIssueReturnGuideForProducts() throws BusinessException {

		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final LocalDate rentDate = LocalDate.now().minusDays(10);
		final Guide guide = new GuideUnitBuider(GuideTemplate.RETURN).withProducts(1).build();

		final UserContext context = this.getUserContext();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				final RentItem ri = (RentItem) result;
				ri.setPlannedQuantity(new BigDecimal(100));
				ri.addLoadedQuantity(new BigDecimal(90));

				ri.calculateTotalRent(rentDate);
				ri.setStockable();
				ri.setLoadingDate(rentDate);
				ri.setLoadStatus();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenAnswer(invocation -> rentItem);

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		Mockito.when(this.stockDAO.findByUuid(ArgumentMatchers.any())).thenReturn(stock);

		Mockito.when(this.rentDAO.findByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent());

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemDAO, Mockito.times(1)).create(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemDAO, Mockito.times(1)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockDAO, Mockito.times(1)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentDAO, Mockito.times(1)).update(context, guide.getRent());
		Mockito.verify(this.guideDAO, Mockito.times(1)).create(context, guide);
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(context, guide.getUnit().getUuid());

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.RETURN, guide.getType());
		Assert.assertEquals(rentItem.getCalculatedTotal(), guide.getRent().getTotalCalculated());
		Assert.assertEquals(LoadStatus.COMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.INCOMPLETE, rentItem.getReturnStatus());
	}

	@Test
	public void shouldIssueReturnGuideForServices() throws BusinessException {

		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final LocalDate rentDate = LocalDate.now().minusDays(10);
		final Guide guide = new GuideUnitBuider(GuideTemplate.RETURN).withProducts(1).build();

		final UserContext context = this.getUserContext();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.SERVICE, result -> {
			if (result instanceof RentItem) {
				final RentItem ri = (RentItem) result;
				ri.setPlannedQuantity(new BigDecimal(100));
				ri.addLoadedQuantity(new BigDecimal(90));

				ri.calculateTotalRent(rentDate);
				ri.setStockable();
				ri.setLoadingDate(rentDate);
				ri.setLoadStatus();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenAnswer(invocation -> rentItem);

		Mockito.when(this.rentDAO.findByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent());

		this.guideService.issueGuide(context, guide);

		Mockito.verify(this.guideItemDAO, Mockito.times(1)).create(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.rentItemDAO, Mockito.times(1)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.stockDAO, Mockito.times(0)).update(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Stock.class));
		Mockito.verify(this.rentDAO, Mockito.times(1)).update(context, guide.getRent());
		Mockito.verify(this.guideDAO, Mockito.times(1)).create(context, guide);
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(context, guide.getUnit().getUuid());

		Assert.assertEquals(LocalDate.now(), guide.getIssueDate());
		Assert.assertEquals(GuideType.RETURN, guide.getType());
		Assert.assertEquals(rentItem.getCalculatedTotal(), guide.getRent().getTotalCalculated());
		Assert.assertEquals(LoadStatus.COMPLETE, rentItem.getLoadStatus());
		Assert.assertEquals(ReturnStatus.INCOMPLETE, rentItem.getReturnStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldIssueReturnGuideForEmptyItems() throws BusinessException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.NO_ITEMS_RETURN).build();

		Mockito.when(this.translator.getTranslation("guide.must.have.items")).thenReturn("Cannot issue return guide without items");

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldIssueReturnGuideToReturnMoreThanLoaded() throws BusinessException {

		this.guideService.setGuideIssuer(this.returnGuideIssuer);
		final LocalDate rentDate = LocalDate.now().minusDays(10);
		final Guide guide = new GuideUnitBuider(GuideTemplate.RETURN).withProducts(5).build();

		final UserContext context = this.getUserContext();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.SERVICE, result -> {
			if (result instanceof RentItem) {
				final RentItem ri = (RentItem) result;
				ri.setPlannedQuantity(new BigDecimal(5));
				ri.addLoadedQuantity(new BigDecimal(5));
				ri.calculateTotalRent(rentDate);
				ri.setStockable();
				ri.setLoadingDate(rentDate);
				ri.setLoadStatus();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenAnswer(invocation -> rentItem);
		Mockito.when(this.translator.getTranslation("guide.return.item.quantity.unexpected", new String[] { rentItem.getItem().getName() }))
		.thenReturn("cannot return more tha loaded itens");

		this.guideService.issueGuide(context, guide);
	}
}
