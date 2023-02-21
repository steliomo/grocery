/**
 *
 */
package mz.co.grocery.core.sale.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.dao.SaleItemDAO;
import mz.co.grocery.core.sale.model.DeliveryStatus;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.model.SaleStatus;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@Service(InstallmentSaleServiceImpl.NAME)
public class InstallmentSaleServiceImpl extends AbstractService implements SaleService {

	public static final String NAME = "mz.co.grocery.core.sale.service.InstallmentSaleServiceImpl";

	@Inject
	private InventoryDAO inventoryDAO;

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private SaleDAO saleDAO;

	@Inject
	private SaleItemDAO saleItemDAO;

	@Inject
	private PaymentService paymentService;

	@Override
	public Sale registSale(final UserContext userContext, final Sale sale) throws BusinessException {

		this.verifyPendingInventory(sale);

		if (sale.getItems().isEmpty()) {
			throw new BusinessException(this.translator.getTranslation("cannot.create.sale.without.items"));
		}

		if (sale.getCustomer() == null) {
			throw new BusinessException(this.translator.getTranslation("installment.sale.must.have.customer"));
		}

		if (sale.getDueDate() == null) {
			throw new BusinessException(this.translator.getTranslation("installment.sale.due.date.must.be.specified"));
		}

		sale.setSaleStatus(SaleStatus.PENDING);
		sale.setTotalPaid(BigDecimal.ZERO);
		sale.setDeliveryStatus(DeliveryStatus.PENDING);

		this.saleDAO.create(userContext, sale);

		for (final SaleItem saleItem : sale.getItems()) {
			saleItem.setSale(sale);
			this.saleItemDAO.create(userContext, saleItem);
		}

		sale.calculateTotal();
		sale.calculateBilling();

		this.paymentService.debitTransaction(userContext, sale.getGrocery().getUuid());

		return sale;
	}

	private void verifyPendingInventory(final Sale sale) throws BusinessException {
		Inventory inventory = null;

		try {
			inventory = this.inventoryDAO.fetchByGroceryAndStatus(sale.getGrocery(), InventoryStatus.PENDING, EntityStatus.ACTIVE);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}

		if (inventory != null) {
			throw new BusinessException(this.translator.getTranslation("cannot.registe.sale.with.pendind.inventory"));
		}
	}
}
