/**
 *
 */
package mz.co.grocery.persistence.rent;

import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.domain.item.ItemType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemPortTest extends AbstractIntegServiceTest {

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	private RentPort rentService;

	@Inject
	private RentItemPort rentItemPort;

	private Rent rent;

	@Before
	public void setup() throws BusinessException {
		this.rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = this.rent.getRentItems().get();
		this.rent = this.rentService.createRent(this.getUserContext(), this.rent);

		for (RentItem rentItem : rentItems) {
			rentItem.setRent(this.rent);
			rentItem.calculatePlannedTotal();
			rentItem.setStockable();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);
		}
	}

	@Test
	public void shouldFetchRentItemByUuid() {
		this.rent.getRentItems().get().stream().forEach(rentItem -> {
			try {
				final RentItem fetchedRentItem = this.rentItemPort.fetchByUuid(rentItem.getUuid());

				if (ItemType.PRODUCT.equals(fetchedRentItem.getItem().get().getType())) {
					Assert.assertNotNull(fetchedRentItem.getItem());
				}

			} catch (final BusinessException e) {
				e.printStackTrace();
			}

		});
	}

}
