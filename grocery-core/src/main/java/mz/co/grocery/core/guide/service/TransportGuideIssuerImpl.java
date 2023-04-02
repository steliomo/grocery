/**
 *
 */
package mz.co.grocery.core.guide.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.guide.dao.GuideDAO;
import mz.co.grocery.core.guide.dao.GuideItemDAO;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentItemDAO;
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

@Service(TransportGuideIssuerImpl.NAME)
public class TransportGuideIssuerImpl implements GuideIssuer {

	public static final String NAME = "mz.co.grocery.core.rent.service.TransportGuideIssuerImpl";

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private GuideDAO guideDAO;

	@Inject
	private StockDAO stockDAO;

	@Inject
	private GuideItemDAO guideItemDAO;

	@Inject
	private RentItemDAO rentItemDAO;

	@Inject
	private RentDAO rentDAO;

	@Override
	public Guide issue(final UserContext context, final Guide guide) throws BusinessException {

		if (!GuideType.TRANSPORT.equals(guide.getType())) {
			throw new BusinessException(this.translator.getTranslation("guide.type.must.be.transport"));
		}

		if (guide.getGuideItems().isEmpty()) {
			throw new BusinessException(this.translator.getTranslation("guide.must.have.items"));
		}

		guide.setIssueDate(LocalDate.now());
		this.guideDAO.create(context, guide);

		BigDecimal totalCalculated = BigDecimal.ZERO;

		for (final GuideItem guideItem : guide.getGuideItems()) {

			guideItem.setGuide(guide);
			this.guideItemDAO.create(context, guideItem);

			final RentItem rentItem = this.rentItemDAO.findByUuid(guideItem.getRentItem().getUuid());

			if (guideItem.getQuantity().compareTo(rentItem.getQuantityToLoad()) > BigDecimal.ZERO.intValue()) {
				throw new BusinessException(
						this.translator.getTranslation("guide.unexpected.item.load.quantity", new String[] { rentItem.getItem().getName() }));
			}

			final BigDecimal chunkValue = rentItem.getRentalChunkValueOnLoading(guide.getIssueDate());
			rentItem.addLoadedQuantity(guideItem.getQuantity());
			rentItem.setLoadingDate(guide.getIssueDate());
			rentItem.setLoadStatus();
			rentItem.setReturnStatus();

			this.rentItemDAO.update(context, rentItem);

			totalCalculated = totalCalculated.add(chunkValue);

			if (rentItem.isStockable()) {
				final Stock stock = this.stockDAO.findByUuid(guideItem.getStock().getUuid());
				stock.subtractStock(guideItem.getQuantity());

				this.stockDAO.update(context, stock);
			}
		}

		final Rent rent = this.rentDAO.fetchByUuid(guide.getRent().getUuid());
		rent.setTotalCalculated(totalCalculated);
		rent.setLoadingStatus();
		this.rentDAO.update(context, rent);

		return guide;
	}

}
