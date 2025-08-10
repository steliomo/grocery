/**
 *
 */
package mz.co.grocery.core.application.rent.service;

import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.application.rent.in.RentUseCase;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class RentService extends AbstractService implements RentUseCase {

	private RentPort rentPort;

	private StockPort stockPort;

	private RentItemPort rentItemPort;

	public RentService(final RentPort rentPort, final StockPort stockPort, final RentItemPort rentItemPort) {
		this.rentPort = rentPort;
		this.stockPort = stockPort;
		this.rentItemPort = rentItemPort;
	}

	@Override
	public Rent rent(final UserContext userContext, Rent rent) throws BusinessException {

		final Set<RentItem> items = rent.getRentItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("cannot.create.rent.without.items");
		}

		final Optional<Rent> existingRent = this.rentPort.findRentByCustomerAndUnitAndStatus(rent.getCustomer().get(), rent.getUnit().get(),
				RentStatus.OPENED);

		if (existingRent.isPresent()) {

			final Rent existing = existingRent.get();

			this.createRentItems(userContext, existing, items);

			existing.calculateTotalEstimated();

			this.rentPort.updateRent(userContext, existing);

		} else {

			rent.setPaymentStatus();

			rent = this.rentPort.createRent(userContext, rent);

			this.createRentItems(userContext, rent, items);

			rent.calculateTotalEstimated();

			this.rentPort.updateRent(userContext, rent);
		}

		return rent;
	}

	private void createRentItems(final UserContext userContext, final Rent rent, final Set<RentItem> items) throws BusinessException {
		for (final RentItem rentItem : items) {

			rentItem.setStockable();
			rentItem.calculatePlannedTotal();
			rentItem.setLoadStatus();

			if (rentItem.isStockable()) {

				final Stock stock = this.stockPort.findStockByUuid(rentItem.getItem().get().getUuid());

				if (rentItem.getPlannedQuantity().doubleValue() > stock.getQuantity().doubleValue()) {
					throw new BusinessException("product.quantity.unavailable", new String[] { stock.getName() });
				}
			}

			rentItem.setRent(rent);
			rent.addRentItem(rentItem);

			this.rentItemPort.createRentItem(userContext, rentItem);
		}
	}
}