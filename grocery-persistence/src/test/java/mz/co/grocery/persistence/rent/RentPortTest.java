/**
 *
 */
package mz.co.grocery.persistence.rent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.TransportGuideIssuer;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.LoadStatus;
import mz.co.grocery.core.domain.rent.PaymentStatus;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.rent.ReturnStatus;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.RentPaymentTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentPortTest extends AbstractIntegServiceTest {

	@Inject
	private RentBuilder builder;

	@Inject
	private RentPort rentPort;

	@Inject
	private IssueGuideUseCase issueGuideUseCase;

	@Inject
	@BeanQualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	private RentPaymentPort rentPaymentPort;

	@Inject
	private RentItemPort rentItemPort;

	private Rent rent;

	private Set<RentItem> rentItems;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.builder.build();
		this.rentItems = this.rent.getRentItems().get();
		this.rent.setPaymentStatus();
		this.rent.setLoadingStatus();

		this.rent = this.rentPort.createRent(this.getUserContext(), this.rent);

		for (RentItem rentItem : this.rentItems) {
			rentItem.setRent(this.rent);
			rentItem.calculatePlannedTotal();
			rentItem.setStockable();
			rentItem.setLoadStatus();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);
		}
	}

	@Test
	public void shouldFindPendingPaymentRentsByCustomer() throws BusinessException {
		final List<Rent> rents = this.rentPort.findPendingPaymentRentsByCustomer(this.rent.getCustomer().get().getUuid());

		Assert.assertFalse(rents.isEmpty());
		rents.forEach(rent -> {
			Assert.assertEquals(PaymentStatus.PENDING, rent.getPaymentStatus());
		});
	}

	@Test
	public void shouldFetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer() throws BusinessException {
		final List<Rent> rents = this.rentPort.fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(this.rent.getCustomer().get().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(rent -> {
			rent.getRentItems().get().forEach(rentItem -> {
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

		for (RentItem rentItem : this.rentItems) {
			rentItem.setRent(this.rent);
			rentItem.calculatePlannedTotal();
			rentItem.setStockable();
			rentItem.setLoadStatus();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		}

		this.issueGuideUseCase.setGuideIssuer(this.transportGuideIssuer);
		this.issueGuideUseCase.issueGuide(this.getUserContext(), guide);

		final List<Rent> rents = this.rentPort.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(this.rent.getCustomer().get().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(rent -> {
			rent.getRentItems().get().forEach(rentItem -> {
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

		for (RentItem rentItem : this.rentItems) {
			rentItem.setRent(this.rent);
			rentItem.calculatePlannedTotal();
			rentItem.setStockable();
			rentItem.setLoadStatus();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		}

		this.issueGuideUseCase.setGuideIssuer(this.transportGuideIssuer);
		this.issueGuideUseCase.issueGuide(this.getUserContext(), guide);

		final List<Rent> rents = this.rentPort.fetchRentsWithIssuedGuidesByTypeAndCustomer(guide.getType(),
				this.rent.getCustomer().get().getUuid());

		Assert.assertFalse(rents.isEmpty());
	}

	@Test
	public void shouldFetchRentsWithPaymentsByCustomer() throws BusinessException {
		RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID, processor -> {
			if (processor instanceof RentPayment) {
				final RentPayment payment = (RentPayment) processor;
				payment.setRent(this.rent);
			}
		});

		rentPayment = this.rentPaymentPort.createRentPayment(this.getUserContext(), rentPayment);

		final List<Rent> rents = this.rentPort.fetchRentsWithPaymentsByCustomer(this.rent.getCustomer().get().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(r -> {
			Assert.assertFalse(r.getRentPayments().get().isEmpty());
			r.getRentPayments().get().forEach(rp -> {
				Assert.assertNotNull(rp);
			});
		});
	}
}
