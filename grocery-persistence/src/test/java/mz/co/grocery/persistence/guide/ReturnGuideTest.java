/**
 *
 */
package mz.co.grocery.persistence.guide;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.guide.service.ReturnGuideIssuerImpl;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.grocery.persistence.rent.RentBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ReturnGuideTest extends AbstractIntegServiceTest {

	@Inject
	private GuideService guideService;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	@Qualifier(ReturnGuideIssuerImpl.NAME)
	private GuideIssuer returnGuideIssuer;

	@Inject
	private RentService rentService;

	@Test
	public void shouldIssueReturnGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);

		final Rent rent = this.rentBuilder.build();
		final LocalDate returnDate = LocalDate.now().minusDays(10);
		rent.setRentDate(returnDate);

		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_RETURN);
		guide.setIssueDate(returnDate);

		rent.getRentItems().forEach(rentItem -> {
			rentItem.getRentalChunkValueOnLoading(guide.getIssueDate());
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
