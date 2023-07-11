/**
 *
 */
package mz.co.grocery.core.application.guide.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

@UseCase(TransportGuideIssuer.NAME)
public class TransportGuideIssuer implements GuideIssuer {

	public static final String NAME = "mz.co.grocery.core.application.guide.service.TransportGuideIssuer";

	private GuidePort guidePort;

	private GuideItemPort guideItemPort;

	private RentItemPort rentItemPort;

	private StockPort stockPort;

	private RentPort rentPort;

	public TransportGuideIssuer(final GuidePort guidePort, final GuideItemPort guideItemPort, final RentItemPort rentItemPort,
			final RentPort rentPort, final StockPort stockPort) {
		this.guidePort = guidePort;
		this.guideItemPort = guideItemPort;
		this.rentItemPort = rentItemPort;
		this.rentPort = rentPort;
		this.stockPort = stockPort;
	}

	@Override
	public Guide issue(final UserContext context, Guide guide) throws BusinessException {

		if (!GuideType.TRANSPORT.equals(guide.getType())) {
			throw new BusinessException("guide.type.must.be.transport");
		}

		final Set<GuideItem> items = guide.getGuideItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("guide.must.have.items");
		}

		guide.setIssueDate(LocalDate.now());
		guide = this.guidePort.createGuide(context, guide);

		BigDecimal totalCalculated = BigDecimal.ZERO;

		for (GuideItem guideItem : items) {

			guideItem.setGuide(guide);
			guideItem = this.guideItemPort.createGuideItem(context, guideItem);

			final RentItem rentItem = this.rentItemPort.findByUuid(guideItem.getRentItem().get().getUuid());

			if (guideItem.getQuantity().compareTo(rentItem.getQuantityToLoad()) > BigDecimal.ZERO.intValue()) {
				throw new BusinessException("guide.unexpected.item.load.quantity", new String[] { rentItem.getItem().get().getName() });
			}

			final BigDecimal chunkValue = rentItem.getRentalChunkValueOnLoading(guide.getIssueDate());
			rentItem.addLoadedQuantity(guideItem.getQuantity());
			rentItem.setLoadingDate(guide.getIssueDate());
			rentItem.setLoadStatus();
			rentItem.setReturnStatus();

			this.rentItemPort.updateRentItem(context, rentItem);

			totalCalculated = totalCalculated.add(chunkValue);

			if (rentItem.isStockable()) {
				final Stock stock = this.stockPort.findStockByUuid(guideItem.getStock().get().getUuid());
				stock.subtractStock(guideItem.getQuantity());

				this.stockPort.updateStock(context, stock);
			}
		}

		final Rent rent = this.rentPort.fetchByUuid(guide.getRent().get().getUuid());
		rent.setTotalCalculated(totalCalculated);
		rent.setLoadingStatus();
		this.rentPort.updateRent(context, rent);

		return guide;
	}

}
