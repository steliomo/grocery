/**
 *
 */
package mz.co.grocery.persistence.guide;

import java.math.BigDecimal;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.TransportGuideIssuer;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.LoadStatus;
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
public class TransportGuideTest extends AbstractIntegServiceTest {

	@Inject
	private IssueGuideUseCase issueGuideUseCase;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	@BeanQualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	private RentPort rentService;

	@Inject
	private RentItemPort rentItemPort;

	@Test
	public void shouldIssueTransportGuide() throws BusinessException {
		this.issueGuideUseCase.setGuideIssuer(this.transportGuideIssuer);

		Rent rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = rent.getRentItems().get();
		rent = this.rentService.createRent(this.getUserContext(), rent);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_TRANSPORT);
		guide.setRent(rent);

		for (RentItem rentItem : rentItems) {
			rentItem.setRent(rent);
			rentItem.calculatePlannedTotal();
			rentItem.setStockable();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();

			guideItem.setRentItem(rentItem);
			guideItem.setQuantity(new BigDecimal(2));

			guide.addGuideItem(guideItem);

			rent.addRentItem(rentItem);
		}

		rent.calculateTotalEstimated();
		this.issueGuideUseCase.issueGuide(this.getUserContext(), guide);

		Assert.assertEquals(GuideType.TRANSPORT, guide.getType());

		for (final GuideItem guideItem : guide.getGuideItems().get()) {

			final RentItem rentItem = this.rentItemPort.findByUuid(guideItem.getRentItem().get().getUuid());

			Assert.assertNotNull(rent.getTotalCalculated());
			Assert.assertNotEquals(BigDecimal.ZERO, rent.getTotalEstimated());
			Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
			Assert.assertEquals(new BigDecimal(2).doubleValue(), rentItem.getLoadedQuantity().doubleValue(), 0.0);
		}
	}
}
