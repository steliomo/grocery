/**
 *
 */
package mz.co.grocery.persistence.rent;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	private RentItemDAO rentItemDAO;

	@Inject
	private RentService rentService;

	private Rent rent;

	@Before
	public void setup() throws BusinessException {
		this.rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), this.rent);
	}

	@Test
	public void shouldFetchRentItemByUuid() {
		this.rent.getRentItems().stream().forEach(rentItem -> {
			try {
				final RentItem fetchedRentItem = this.rentItemDAO.fetchByUuid(rentItem.getUuid());

				if (ItemType.PRODUCT.equals(fetchedRentItem.getItem().getType())) {
					Assert.assertNotNull(fetchedRentItem.getItem());
				}

			} catch (final BusinessException e) {
				e.printStackTrace();
			}

		});
	}

}
