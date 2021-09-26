/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(RentServiceImpl.NAME)
public class RentServiceImpl extends AbstractService implements RentService {

	public static final String NAME = "mz.co.grocery.core.rent.service.RentServiceImpl";

	@Inject
	private StockDAO stockDAO;

	@Inject
	private RentItemDAO rentItemDAO;

	@Inject
	private RentDAO rentDAO;

	@Inject
	private PaymentService paymentService;

	@Override
	public Rent rent(final UserContext userContext, final Rent rent) throws BusinessException {

		if (rent.getRentItems().isEmpty()) {
			throw new BusinessException("Cannot create a rent without items");
		}

		rent.setPaymentStatus();
		this.rentDAO.create(userContext, rent);

		for (final RentItem rentItem : rent.getRentItems()) {

			rentItem.setReturnable().setReturnStatus(BigDecimal.ZERO);
			rentItem.setTotal();

			if (ItemType.PRODUCT.equals(rentItem.getType())) {

				final Stock stock = this.stockDAO.findByUuid(rentItem.getItem().getUuid());

				if (rentItem.getQuantity().doubleValue() > stock.getQuantity().doubleValue()) {
					throw new BusinessException("The product quantity is not available");
				}

				stock.subtractStock(rentItem.getQuantity());
				this.stockDAO.update(userContext, stock);
			}

			rentItem.setRent(rent);
			this.rentItemDAO.create(userContext, rentItem);
		}

		this.paymentService.debitTransaction(userContext, rent.getUnit().getUuid());
		return rent;
	}
}