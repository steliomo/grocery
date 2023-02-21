/**
 *
 */
package mz.co.grocery.core.guide.service;

import java.time.LocalDate;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.guide.dao.GuideDAO;
import mz.co.grocery.core.guide.dao.GuideItemDAO;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.dao.SaleItemDAO;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.core.util.BigDecimalUtil;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@Service(DeliveryGuideIssuerImpl.NAME)
public class DeliveryGuideIssuerImpl implements GuideIssuer {

	public static final String NAME = " mz.co.grocery.core.guide.service.DeliveryGuideIssuerImpl";

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private GuideDAO guideDAO;

	@Inject
	private GuideItemDAO guideItemDAO;

	@Inject
	private SaleItemDAO saleItemDAO;

	@Inject
	private StockDAO stockDAO;

	@Inject
	private SaleDAO saleDAO;

	@Override
	public Guide issue(final UserContext userContext, final Guide guide) throws BusinessException {

		if (!GuideType.DELIVERY.equals(guide.getType())) {
			throw new BusinessException(this.translator.getTranslation("guide.type.must.be.delivery"));
		}

		if (!SaleType.INSTALLMENT.equals(guide.getSale().getSaleType())) {
			throw new BusinessException(this.translator.getTranslation("guide.sale.type.must.be.installment"));
		}

		if (guide.getGuideItems().isEmpty()) {
			throw new BusinessException(this.translator.getTranslation("guide.must.have.items"));
		}

		guide.setIssueDate(LocalDate.now());
		this.guideDAO.create(userContext, guide);

		for (final GuideItem guideItem : guide.getGuideItems()) {

			guideItem.setGuide(guide);
			this.guideItemDAO.create(userContext, guideItem);

			final SaleItem saleItem = this.saleItemDAO.findByUuid(guideItem.getSaleItem().getUuid());

			if (BigDecimalUtil.isGraterThan(guideItem.getQuantity(), saleItem.getQuantityToDeliver())) {
				throw new BusinessException(
						this.translator.getTranslation("guide.unexpected.itemas.quantity.to.delivery", new String[] { saleItem.getName() }));
			}

			saleItem.addDeliveredQuantity(guideItem.getQuantity());
			saleItem.setDeliveryStatus();
			this.saleItemDAO.update(userContext, saleItem);

			if (saleItem.isProduct()) {
				final Stock stock = this.stockDAO.findByUuid(guideItem.getStock().getUuid());
				stock.subtractStock(guideItem.getQuantity());
				this.stockDAO.update(userContext, stock);
			}
		}

		final Sale sale = this.saleDAO.fetchByUuid(guide.getSale().getUuid());
		sale.updateDeliveryStatus();

		this.saleDAO.update(userContext, sale);

		return guide;
	}
}
