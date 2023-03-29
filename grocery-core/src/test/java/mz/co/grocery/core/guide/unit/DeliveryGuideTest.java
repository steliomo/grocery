/**
 *
 */
package mz.co.grocery.core.guide.unit;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.file.service.FileGeneratorService;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.GuideItemTemplate;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.guide.builder.GuideUnitBuider;
import mz.co.grocery.core.guide.dao.GuideDAO;
import mz.co.grocery.core.guide.dao.GuideItemDAO;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideItemType;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.guide.service.DeliveryGuideIssuerImpl;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.guide.service.GuideServiceImpl;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.dao.SaleItemDAO;
import mz.co.grocery.core.sale.model.DeliveryStatus;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class DeliveryGuideTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final GuideService guideService = new GuideServiceImpl();

	@InjectMocks
	private final GuideIssuer deliveryGuideIssuer = new DeliveryGuideIssuerImpl();

	@Mock
	private ApplicationTranslator translator;

	@Mock
	private GuideDAO guideDAO;

	@Mock
	private GuideItemDAO guideItemDAO;

	@Mock
	private SaleItemDAO saleItemDAO;

	@Mock
	private StockDAO stockDAO;

	@Mock
	private PaymentService paymentService;

	@Mock
	private SaleDAO saleDAO;

	@Mock
	private FileGeneratorService fileGeneratorService;

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideForNonDeliveryGuideType() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.TRANSPORT);
		Mockito.when(this.translator.getTranslation("guide.type.must.be.delivery")).thenReturn("The guide type must be DELIVERY");

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideForNonInstallmentSales() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);
		guide.getSale().setSaleType(SaleType.CASH);

		Mockito.when(this.translator.getTranslation("guide.sale.type.must.be.installment")).thenReturn("The sale type must be installmnet.");

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideWithoutGuideItems() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);
		guide.getSale().setSaleType(SaleType.INSTALLMENT);

		Mockito.when(this.translator.getTranslation("guide.must.have.items")).thenReturn("Guide Items list is empty");

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueGuideForGuideItemsWithQuantityAboveGreaterThanToDeliver() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);
		guide.getSale().setSaleType(SaleType.INSTALLMENT);
		final GuideItem guideItem = EntityFactory.gimme(GuideItem.class, GuideItemTemplate.SALE_PRODUCTS);
		guideItem.setQuantity(new BigDecimal(500));
		guide.addGuideItem(guideItem);

		Mockito.when(this.saleItemDAO.findByUuid(ArgumentMatchers.any())).thenReturn(guideItem.getSaleItem());
		Mockito.when(
				this.translator.getTranslation(ArgumentMatchers.any(), new String[] { ArgumentMatchers.any() }))
		.thenReturn("Unexpected quantity to deliver.");

		this.guideService.issueGuide(this.getUserContext(), guide);
	}

	@Test
	public void shouldIssueDeliveryGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);
		final Guide guide = new GuideUnitBuider(GuideType.DELIVERY.toString()).withSaleProducts(3).build();
		guide.getSale().setSaleType(SaleType.INSTALLMENT);
		guide.getSale().setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		final SaleItem saleItem = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.PRODUCT);
		guide.getSale().addItem(saleItem);

		Mockito.when(this.saleItemDAO.findByUuid(ArgumentMatchers.any())).thenReturn(saleItem);
		Mockito.when(this.stockDAO.findByUuid(ArgumentMatchers.any())).thenReturn(saleItem.getStock());
		Mockito.when(this.saleDAO.fetchByUuid(ArgumentMatchers.any())).thenReturn(guide.getSale());

		this.guideService.issueGuide(this.getUserContext(), guide);

		Mockito.verify(this.guideDAO, Mockito.times(1)).create(this.getUserContext(), guide);
		Mockito.verify(this.guideItemDAO, Mockito.times(3)).create(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(GuideItem.class));
		Mockito.verify(this.saleItemDAO, Mockito.times(3)).update(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(SaleItem.class));
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(this.getUserContext(), guide.getUnit().getUuid());
		Mockito.verify(this.saleDAO, Mockito.times(1)).fetchByUuid(ArgumentMatchers.any());
		Mockito.verify(this.saleDAO, Mockito.times(1)).update(ArgumentMatchers.any(), ArgumentMatchers.any());

		Assert.assertEquals(GuideType.DELIVERY, guide.getType());

		Assert.assertNotNull(guide);
		Assert.assertEquals(DeliveryStatus.INCOMPLETE, guide.getSale().getDeliveryStatus());

		guide.getGuideItems().forEach(guideItem -> {
			Assert.assertEquals(GuideItemType.SALE, guideItem.getItemGuideType());
		});
	}
}
