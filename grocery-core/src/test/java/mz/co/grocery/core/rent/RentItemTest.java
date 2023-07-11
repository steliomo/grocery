/**
 *
 */
package mz.co.grocery.core.rent;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemTest extends AbstractUnitServiceTest {

	private RentItem rentItem;

	@Before
	public void setup() {
		this.rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT);
		this.rentItem.calculatePlannedTotal();
	}

	@Test
	public void shouldCalculateTotalPlanned() {
		final BigDecimal expected = this.rentItem.getItem().get().getRentPrice().multiply(this.rentItem.getPlannedDays())
				.multiply(this.rentItem.getPlannedQuantity())
				.subtract(this.rentItem.getDiscount());

		Assert.assertEquals(expected, this.rentItem.getPlannedTotal());
	}

	@Test
	public void shouldReCalculateTotalPlanned() {
		final BigDecimal returnedQuantity = new BigDecimal(10);
		final LocalDate returnDate = LocalDate.now();
		this.rentItem.setLoadingDate(returnDate);

		final BigDecimal expected = this.rentItem.getItem().get().getRentPrice().multiply(this.rentItem.getPlannedDays())
				.multiply(this.rentItem.getPlannedQuantity().subtract(returnedQuantity))
				.subtract(this.rentItem.getDiscount());

		this.rentItem.reCalculateTotalPlanned(returnDate, returnedQuantity);

		Assert.assertEquals(expected, this.rentItem.getPlannedTotal());
	}

	@Test
	public void shouldCalculateRentalChunkValueOnReturning() {

		final BigDecimal returnedQuantity = new BigDecimal(10);
		final LocalDate returnDate = LocalDate.now();
		this.rentItem.setLoadingDate(returnDate.minusDays(5));
		this.rentItem.addLoadedQuantity(new BigDecimal(30));

		final long days = Duration.between(this.rentItem.getLoadingDate().atStartOfDay(), returnDate.atStartOfDay()).toDays();
		final BigDecimal expected = this.rentItem.getItem().get().getRentPrice().multiply(new BigDecimal(days))
				.multiply(returnedQuantity);

		final BigDecimal rentalChunkValue = this.rentItem.getRentalChunkValueOnReturning(returnDate, returnedQuantity);

		Assert.assertEquals(expected, rentalChunkValue);
	}

	@Test
	public void shouldCalculateRentalChunkValueOnLoading() {

		final LocalDate loadDate = LocalDate.now();

		this.rentItem.setLoadingDate(loadDate.minusDays(5));
		this.rentItem.addLoadedQuantity(new BigDecimal(10));

		final long days = Duration.between(this.rentItem.getLoadingDate().atStartOfDay(), loadDate.atStartOfDay()).toDays();

		final BigDecimal expected = this.rentItem.getItem().get().getRentPrice().multiply(new BigDecimal(days))
				.multiply(this.rentItem.getLoadedQuantity());

		final BigDecimal rentalChunkValue = this.rentItem.getRentalChunkValueOnLoading(loadDate);

		Assert.assertEquals(expected, rentalChunkValue);
	}
}
