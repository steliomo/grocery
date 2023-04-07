/**
 *
 */
package mz.co.grocery.persistence.guide;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.guide.service.TransportGuideIssuerImpl;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.LoadStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
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
public class TransportGuideTest extends AbstractIntegServiceTest {

	@Inject
	private GuideService guideService;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	@Qualifier(TransportGuideIssuerImpl.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	private RentService rentService;

	@Inject
	private RentItemDAO rentItemDAO;

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
}
