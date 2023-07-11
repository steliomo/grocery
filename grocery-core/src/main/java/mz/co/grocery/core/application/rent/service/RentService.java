/**
 *
 */
package mz.co.grocery.core.application.rent.service;

import java.util.Optional;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
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

	private PaymentUseCase paymentUseCase;

	public RentService(final RentPort rentPort, final StockPort stockPort, final RentItemPort rentItemPort, final PaymentUseCase paymentUseCase) {
		this.rentPort = rentPort;
		this.stockPort = stockPort;
		this.rentItemPort = rentItemPort;
		this.paymentUseCase = paymentUseCase;
	}

	@Override
	public Rent rent(final UserContext userContext, final Rent rent) throws BusinessException {

		if (rent.getRentItems().get().isEmpty()) {
			throw new BusinessException("cannot.create.rent.without.items");
		}

		final Optional<Rent> existingRent = this.rentPort.findRentByCustomerAndUnitAndStatus(rent.getCustomer().get(), rent.getUnit().get(),RentStatus.OPENED);

		if (!existingRent.isPresent()) {
			this.rentPort.createRent(userContext, rent);
		}

		for (final RentItem rentItem : rent.getRentItems().get()) {

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
			existingRent.ifPresent(existing -> rentItem.setRent(existing));

			this.rentItemPort.createRentItem(userContext, rentItem);
		}

		rent.calculateTotalEstimated();
		rent.setPaymentStatus();

		if(existingRent.isPresent()) {
			final Rent existing = existingRent.get();
			existing.updateTotalEstimated(rent.getTotalEstimated());

			this.rentPort.updateRent(userContext, existing);
		}

		this.paymentUseCase.debitTransaction(userContext, rent.getUnit().get().getUuid());

		return rent;
	}
}