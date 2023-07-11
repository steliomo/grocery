/**
 *
 */
package mz.co.grocery.core.application.guide.service;

import java.math.BigDecimal;
import java.util.Set;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.out.GuideItemPort;
import mz.co.grocery.core.application.guide.out.GuidePort;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@UseCase(ReturnGuideIssuer.NAME)
public class ReturnGuideIssuer implements GuideIssuer {

	public static final String NAME = "mz.co.grocery.core.application.guide.service.ReturnGuideIssuer";

	private GuidePort guidePort;

	private GuideItemPort guideItemPort;

	private RentItemPort rentItemPort;

	private StockPort stockPort;

	private RentPort rentPort;

	public ReturnGuideIssuer(final GuidePort guidePort, final GuideItemPort guideItemPort, final RentItemPort rentItemPort, final StockPort stockPort,
			final RentPort rentPort) {
		this.guidePort = guidePort;
		this.guideItemPort = guideItemPort;
		this.rentItemPort = rentItemPort;
		this.stockPort = stockPort;
		this.rentPort = rentPort;
	}

	@Override
	public Guide issue(final UserContext userContext, Guide guide) throws BusinessException {

		if (!GuideType.RETURN.equals(guide.getType())) {
			throw new BusinessException("guide.type.must.be.return");
		}

		final Set<GuideItem> items = guide.getGuideItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("guide.must.have.items");
		}

		guide = this.guidePort.createGuide(userContext, guide);

		BigDecimal calculatedTotalRental = BigDecimal.ZERO;

		for (GuideItem guideItem : items) {

			guideItem.setGuide(guide);
			guideItem = this.guideItemPort.createGuideItem(userContext, guideItem);

			final RentItem rentItem = this.rentItemPort.findByUuid(guideItem.getRentItem().get().getUuid());

			if (guideItem.getQuantity().compareTo(rentItem.getCurrentRentQuantity()) > BigDecimal.ZERO.intValue()) {
				throw new BusinessException("guide.return.item.quantity.unexpected", new String[] { rentItem.getItem().get().getName() });
			}

			if (guide.getIssueDate().isBefore(rentItem.getLoadingDate())) {
				throw new BusinessException("guide.return.date.unexpected", new String[] { rentItem.getItem().get().getName() });
			}

			final BigDecimal rentalChunk = rentItem.getRentalChunkValueOnReturning(guide.getIssueDate(), guideItem.getQuantity());
			rentItem.reCalculateTotalPlanned(guide.getIssueDate(), guideItem.getQuantity());

			rentItem.addReturnedQuantity(guideItem.getQuantity());
			rentItem.setReturnDate(guide.getIssueDate());
			rentItem.setReturnStatus();
			rentItem.closeItemLoad();

			this.rentItemPort.updateRentItem(userContext, rentItem);

			calculatedTotalRental = calculatedTotalRental.add(rentalChunk);

			if (rentItem.isStockable()) {
				final Stock stock = this.stockPort.findStockByUuid(rentItem.getItem().get().getUuid());
				stock.addQuantity(guideItem.getQuantity());

				this.stockPort.updateStock(userContext, stock);
			}
		}

		final Rent rent = this.rentPort.fetchByUuid(guide.getRent().get().getUuid());
		rent.setTotalCalculated(calculatedTotalRental);
		rent.calculateTotalEstimated();
		rent.setReturnStatus();
		rent.setPaymentStatus();
		rent.closeRentStatus();

		this.rentPort.updateRent(userContext, rent);

		return guide;
	}
}
