/**
 *
 */
package mz.co.grocery.core.guide.unit;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.file.service.FileGeneratorService;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.guide.builder.GuideUnitBuider;
import mz.co.grocery.core.guide.dao.GuideDAO;
import mz.co.grocery.core.guide.dao.GuideItemDAO;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.guide.service.GuideServiceImpl;
import mz.co.grocery.core.guide.service.TransportGuideIssuerImpl;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.LoadStatus;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.ReturnStatus;
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
public class TransportGuideTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final GuideService guideService = new GuideServiceImpl();

	@InjectMocks
	private final GuideIssuer transportGuideIssuer = new TransportGuideIssuerImpl();

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

	@Mock
	private FileGeneratorService fileGeneratorService;

	@Test
	public void shouldIssueTransportGuideForProducts() throws BusinessException {

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withRentProducts(2).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.stockDAO.findByUuid(ArgumentMatchers.any())).thenReturn(EntityFactory.gimme(Stock.class,
				StockTemplate.VALID));

		Mockito.when(this.rentDAO.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent());

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
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withRentProducts(20).build();

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
		final Guide guide = new GuideUnitBuider(GuideTemplate.TRANSPORT).withRentProducts(2).build();

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.SERVICE, result -> {
			if (result instanceof RentItem) {
				((RentItem) result).setStockable();
			}
		});

		Mockito.when(this.rentItemDAO.findByUuid(ArgumentMatchers.any()))
		.thenReturn(rentItem);

		Mockito.when(this.rentDAO.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getRent());

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

}
