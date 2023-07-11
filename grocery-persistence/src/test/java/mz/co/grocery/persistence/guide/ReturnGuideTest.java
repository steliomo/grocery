/**
 *
 */
package mz.co.grocery.persistence.guide;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.ReturnGuideIssuer;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.grocery.persistence.rent.RentBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ReturnGuideTest extends AbstractIntegServiceTest {

	@Inject
	private IssueGuideUseCase guideService;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	@BeanQualifier(ReturnGuideIssuer.NAME)
	private GuideIssuer returnGuideIssuer;

	@Inject
	private RentPort rentPort;

	@Inject
	RentItemPort rentItemPort;

	@Test
	public void shouldIssueReturnGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);

		Rent rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = rent.getRentItems().get();
		rent = this.rentPort.createRent(this.getUserContext(), rent);

		final LocalDate returnDate = LocalDate.now().minusDays(10);
		rent.setRentDate(returnDate);

		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_RETURN);
		guide.setIssueDate(returnDate);

		for (RentItem rentItem : rentItems) {
			rentItem.setRent(rent);
			rentItem.getRentalChunkValueOnLoading(guide.getIssueDate());
			rentItem.addLoadedQuantity(new BigDecimal(5));
			rentItem.setLoadingDate(guide.getIssueDate());
			rentItem.setLoadStatus();
			rentItem.calculatePlannedTotal();
			rentItem.setStockable();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setRentItem(rentItem);
			guideItem.setQuantity(new BigDecimal(5));

			guide.addGuideItem(guideItem);
		}

		guide.setRent(rent);

		this.guideService.issueGuide(this.getUserContext(), guide);

		Assert.assertEquals(GuideType.RETURN, guide.getType());

		guide.getGuideItems().get().forEach(guideItem -> {
			Assert.assertEquals(new BigDecimal(5), guideItem.getQuantity());
			Assert.assertNotNull(guideItem.getGuide());
		});
	}
}
