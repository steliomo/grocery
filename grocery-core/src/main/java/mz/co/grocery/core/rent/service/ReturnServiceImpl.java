/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.dao.ReturnItemDAO;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.ReturnItem;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ReturnServiceImpl.NAME)
public class ReturnServiceImpl extends AbstractService implements ReturnService {

	public static final String NAME = "mz.co.grocery.core.rent.service.ReturnServiceImpl";

	@Inject
	private RentItemDAO rentItemDAO;

	@Inject
	private ReturnItemDAO returnItemDAO;

	@Inject
	StockDAO stockDAO;

	@Override
	public List<ReturnItem> returnItems(final UserContext userContext, final List<ReturnItem> returnItems) throws BusinessException {

		for (final ReturnItem returnItem : returnItems) {

			if (!returnItem.getRentItem().isReturnable()) {
				throw new BusinessException("This item is not returnable!");
			}

			final RentItem rentItem = this.rentItemDAO.fetchByUuid(returnItem.getRentItem().getUuid());
			returnItem.setRentItem(rentItem);

			final Stock stock = (Stock) rentItem.getItem();
			stock.addQuantity(returnItem.getQuantity());
			stock.setStockStatus();
			this.stockDAO.update(userContext, stock);

			this.returnItemDAO.create(userContext, returnItem);

			rentItem.setReturnStatus(returnItem.getQuantity());
			this.rentItemDAO.update(userContext, rentItem);
		}

		return returnItems;
	}

}
