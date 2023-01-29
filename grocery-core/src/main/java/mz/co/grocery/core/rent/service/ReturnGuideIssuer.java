/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.rent.dao.GuideDAO;
import mz.co.grocery.core.rent.dao.GuideItemDAO;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideItem;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@Service(ReturnGuideIssuer.NAME)
public class ReturnGuideIssuer implements GuideIssuer {

	public static final String NAME = "mz.co.grocery.core.contract.service.ReturnGuideIssuer";

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private GuideDAO guideDAO;

	@Inject
	private RentItemDAO rentItemDAO;

	@Inject
	private StockDAO stockDAO;

	@Inject
	private GuideItemDAO guideItemDAO;

	@Inject
	private RentDAO rentDAO;

	@Override
	public Guide issue(final UserContext userContext, final Guide guide) throws BusinessException {

		if (!GuideType.RETURN.equals(guide.getType())) {
			throw new BusinessException(this.translator.getTranslation("guide.type.must.be.return"));
		}

		if (guide.getGuideItems().isEmpty()) {
			throw new BusinessException(this.translator.getTranslation("guide.must.have.items"));
		}

		guide.setIssueDate(LocalDate.now());
		this.guideDAO.create(userContext, guide);

		BigDecimal calculatedTotal = BigDecimal.ZERO;

		for (final GuideItem guideItem : guide.getGuideItems()) {

			guideItem.setGuide(guide);
			this.guideItemDAO.create(userContext, guideItem);

			final RentItem rentItem = this.rentItemDAO.findByUuid(guideItem.getRentItem().getUuid());

			if (guideItem.getQuantity().compareTo(rentItem.getCurrentRentQuantity()) > BigDecimal.ZERO.intValue()) {
				throw new BusinessException(
						this.translator.getTranslation("guide.return.item.quantity.unexpected", new String[] { rentItem.getItem().getName() }));
			}

			rentItem.calculateTotalOnReturn(guide.getIssueDate(), guideItem.getQuantity());
			rentItem.addReturnedQuantity(guideItem.getQuantity());
			rentItem.setReturnDate(guide.getIssueDate());
			rentItem.setReturnStatus();
			rentItem.closeItemLoad();
			this.rentItemDAO.update(userContext, rentItem);

			calculatedTotal = calculatedTotal.add(rentItem.getCalculatedTotal());

			if (rentItem.isStockable()) {
				final Stock stock = this.stockDAO.findByUuid(rentItem.getItem().getUuid());
				stock.addQuantity(guideItem.getQuantity());

				this.stockDAO.update(userContext, stock);
			}
		}

		final Rent rent = this.rentDAO.fetchByUuid(guide.getRent().getUuid());
		rent.setTotalCalculated(calculatedTotal);
		rent.setReturnStatus();
		rent.setPaymentStatus();
		this.rentDAO.update(userContext, rent);

		return guide;
	}
}
