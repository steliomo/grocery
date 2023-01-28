/**
 *
 */
package mz.co.grocery.core.rent.integ;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GuideTemplate;
import mz.co.grocery.core.rent.builder.RentBuilder;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideItem;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.core.rent.model.LoadStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.service.GuideIssuer;
import mz.co.grocery.core.rent.service.GuideService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.rent.service.ReturnGuideIssuer;
import mz.co.grocery.core.rent.service.TransportGuideIssuer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class GuideServiceTest extends AbstractIntegServiceTest {

	@Inject
	private GuideService guideService;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	@Qualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	@Qualifier(ReturnGuideIssuer.NAME)
	private GuideIssuer returnGuideIssuer;

	@Inject
	private RentService rentService;

	@Inject
	RentItemDAO rentItemDAO;

	@Test
	public void shouldIssueTransportGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.transportGuideIssuer);

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_TRANSPORT);
		guide.setRent(rent);

		rent.getRentItems().forEach(rentItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setRentItem(rentItem);
			guideItem.setQuantity(new BigDecimal(2));

			guide.addGuideItem(guideItem);
		});

		this.guideService.issueGuide(this.getUserContext(), guide);

		TestUtil.assertCreation(guide);
		Assert.assertEquals(GuideType.TRANSPORT, guide.getType());

		for (final GuideItem guideItem : guide.getGuideItems()) {

			final RentItem rentItem = this.rentItemDAO.findById(guideItem.getRentItem().getId());

			TestUtil.assertCreation(guideItem);
			Assert.assertNotNull(guide.getRent().getTotalCalculated());
			Assert.assertNotEquals(BigDecimal.ZERO, guide.getRent().getTotalEstimated());
			Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
			Assert.assertEquals(new BigDecimal(2).doubleValue(), rentItem.getLoadedQuantity().doubleValue(), 0.0);
		}
	}

	@Test
	public void shouldIssueReturnGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);

		final Rent rent = this.rentBuilder.build();
		final LocalDate returnDate = LocalDate.now().minusDays(10);
		rent.setRentDate(returnDate);

		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_RETURN);
		guide.setIssueDate(returnDate);

		rent.getRentItems().forEach(rentItem -> {
			rentItem.calculateTotalRent(guide.getIssueDate());
			rentItem.addLoadedQuantity(new BigDecimal(5));
			rentItem.setLoadingDate(guide.getIssueDate());
			rentItem.setLoadStatus();

			final GuideItem guideItem = new GuideItem();
			guideItem.setRentItem(rentItem);
			guideItem.setQuantity(new BigDecimal(5));

			guide.addGuideItem(guideItem);
		});

		this.rentService.rent(this.getUserContext(), rent);
		guide.setRent(rent);

		this.guideService.issueGuide(this.getUserContext(), guide);

		TestUtil.assertCreation(guide);
		Assert.assertEquals(GuideType.RETURN, guide.getType());

		guide.getGuideItems().forEach(guideItem -> {
			TestUtil.assertCreation(guideItem);
			Assert.assertEquals(new BigDecimal(5), guideItem.getQuantity());
			Assert.assertNotNull(guideItem.getGuide());
		});

	}
}
