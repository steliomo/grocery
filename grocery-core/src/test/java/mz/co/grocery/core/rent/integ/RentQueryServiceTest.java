/**
 *
 */
package mz.co.grocery.core.rent.integ;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.core.rent.builder.RentBuilder;
import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideItem;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.core.rent.model.LoadStatus;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.model.ReturnStatus;
import mz.co.grocery.core.rent.service.GuideIssuer;
import mz.co.grocery.core.rent.service.GuideService;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentQueryService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.rent.service.TransportGuideIssuer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private RentBuilder builder;

	@Inject
	private RentService rentService;

	@Inject
	private RentQueryService rentQueryService;

	@Inject
	private GuideService guideService;

	@Inject
	@Qualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	private RentPaymentService rentPaymentService;

	private Rent rent;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.builder.build();
		this.rentService.rent(this.getUserContext(), this.rent);
	}

	@Test
	public void shouldFindPendingPaymentRentsByCustomer() throws BusinessException {
		final List<Rent> rents = this.rentQueryService.findPendingPaymentRentsByCustomer(this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());
		rents.forEach(rent -> {
			Assert.assertEquals(PaymentStatus.PENDING, rent.getPaymentStatus());
		});
	}

	@Test
	public void shouldFetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer() throws BusinessException {
		final List<Rent> rents = this.rentQueryService.fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(rent -> {
			rent.getRentItems().forEach(rentItem -> {
				Assert.assertEquals(LoadStatus.PENDING, rentItem.getLoadStatus());
				Assert.assertNotNull(rentItem.getItem());
			});
		});
	}

	@Test
	public void shouldFetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer() throws BusinessException {
		final Guide guide = new Guide();
		guide.setRent(this.rent);
		guide.setType(GuideType.TRANSPORT);

		this.rent.getRentItems().forEach(rentItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		});
		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Rent> rents = this.rentQueryService.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(rent -> {
			rent.getRentItems().forEach(rentItem -> {
				Assert.assertEquals(LoadStatus.INCOMPLETE, rentItem.getLoadStatus());
				Assert.assertEquals(ReturnStatus.PENDING, rentItem.getReturnStatus());
				Assert.assertNotNull(rentItem.getItem());
			});
		});
	}

	@Test
	public void shouldGFetchRentsWithIssuedGuidesByTypeAndCustomer() throws BusinessException {
		final Guide guide = new Guide();
		guide.setRent(this.rent);
		guide.setType(GuideType.TRANSPORT);

		this.rent.getRentItems().forEach(rentItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		});
		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Rent> rents = this.rentQueryService.fetchRentsWithIssuedGuidesByTypeAndCustomer(guide.getType(),
				this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());
		rents.forEach(rent -> {
			Assert.assertFalse(rent.getGuides().isEmpty());
			rent.getGuides().forEach(g -> {
				Assert.assertFalse(g.getGuideItems().isEmpty());
			});
		});
	}

	@Test
	public void shouldFetchRentsWithPaymentsByCustomer() throws BusinessException {
		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID, processor -> {
			if (processor instanceof RentPayment) {
				final RentPayment payment = (RentPayment) processor;
				payment.setRent(this.rent);
			}
		});

		this.rentPaymentService.makeRentPayment(this.getUserContext(), rentPayment);

		final List<Rent> rents = this.rentQueryService.fetchRentsWithPaymentsByCustomer(this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(r -> {
			Assert.assertFalse(r.getRentPayments().isEmpty());
			r.getRentPayments().forEach(rp -> {
				Assert.assertNotNull(rp);
			});
		});
	}
}
