/**
 *
 */
package mz.co.grocery.core.application.guide.service;

import java.time.LocalDate;
import java.util.Set;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.out.GuideItemPort;
import mz.co.grocery.core.application.guide.out.GuidePort;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@UseCase(DeliveryGuideIssuer.NAME)
public class DeliveryGuideIssuer implements GuideIssuer {

	public static final String NAME = "mz.co.grocery.core.application.guide.service.DeliveryGuideIssuer";

	private GuidePort guidePort;

	private SaleItemPort saleItemPort;

	private SalePort salePort;

	private StockPort stockPort;

	private GuideItemPort guideItemPort;

	public DeliveryGuideIssuer(final GuidePort guidePort, final GuideItemPort guideItemPort,
			final SaleItemPort saleItemPort,
			final StockPort stockPort,
			final SalePort salePort) {
		this.guidePort = guidePort;
		this.guideItemPort = guideItemPort;
		this.saleItemPort = saleItemPort;
		this.stockPort = stockPort;
		this.salePort = salePort;
	}

	@Override
	public Guide issue(final UserContext userContext, Guide guide) throws BusinessException {

		if (!GuideType.DELIVERY.equals(guide.getType())) {
			throw new BusinessException("guide.type.must.be.delivery");
		}

		if (!SaleType.INSTALLMENT.equals(guide.getSale().get().getSaleType())) {
			throw new BusinessException("guide.sale.type.must.be.installment");
		}

		final Set<GuideItem> guideItems = guide.getGuideItems().get();

		if (guideItems.isEmpty()) {
			throw new BusinessException("guide.must.have.items");
		}

		guide.setIssueDate(LocalDate.now());

		guide = this.guidePort.createGuide(userContext, guide);

		for (final GuideItem guideItem : guideItems) {

			guideItem.setGuide(guide);
			this.guideItemPort.createGuideItem(userContext, guideItem);

			final SaleItem saleItem = this.saleItemPort.findByUuid(guideItem.getSaleItem().get().getUuid());

			if (BigDecimalUtil.isGraterThan(guideItem.getQuantity(), saleItem.getQuantityToDeliver())) {
				throw new BusinessException(
						"guide.unexpected.itemas.quantity.to.delivery", new String[] { saleItem.getName() });
			}

			saleItem.addDeliveredQuantity(guideItem.getQuantity());
			saleItem.setDeliveryStatus();
			saleItem.setSale(guide.getSale().get());

			this.saleItemPort.updateSaleItem(userContext, saleItem);

			if (saleItem.isProduct()) {
				final Stock stock = this.stockPort.findStockByUuid(saleItem.getStock().get().getUuid());
				stock.subtractStock(guideItem.getQuantity());
				this.stockPort.updateStock(userContext, stock);
			}
		}

		final Sale sale = this.salePort.fetchByUuid(guide.getSale().get().getUuid());
		sale.updateDeliveryStatus();

		this.salePort.updateSale(userContext, sale);

		return guide;
	}
}
