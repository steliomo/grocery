/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.math.BigDecimal;
import java.util.Set;

import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.sale.in.SaleUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase(InstallmentSaleService.NAME)
public class InstallmentSaleService extends AbstractService implements SaleUseCase {

	public static final String NAME = "mz.co.grocery.core.sale.service.InstallmentSaleService";

	private SalePort salePort;

	private SaleItemPort saleItemPort;

	private PaymentUseCase paymentUseCase;

	private InventoryPort inventoryPort;

	public InstallmentSaleService(final SalePort salePort, final SaleItemPort saleItemPort, final PaymentUseCase paymentUseCase,
			final InventoryPort inventoryPort) {
		this.salePort = salePort;
		this.saleItemPort = saleItemPort;
		this.paymentUseCase = paymentUseCase;
		this.inventoryPort = inventoryPort;
	}

	@Override
	public Sale registSale(final UserContext userContext, Sale sale) throws BusinessException {

		this.verifyPendingInventory(sale);

		final Set<SaleItem> items = sale.getItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("cannot.create.sale.without.items");
		}

		if (sale.getCustomer() == null) {
			throw new BusinessException("installment.sale.must.have.customer");
		}

		if (sale.getDueDate() == null) {
			throw new BusinessException("installment.sale.due.date.must.be.specified");
		}

		sale.setSaleStatus(SaleStatus.OPENED);
		sale.setTotalPaid(BigDecimal.ZERO);
		sale.setDeliveryStatus(DeliveryStatus.PENDING);
		sale.calculateTotal();
		sale.calculateBilling();

		sale = this.salePort.createSale(userContext, sale);

		for (SaleItem saleItem : items) {
			saleItem.setSale(sale);

			saleItem.setDeliveredQuantity(BigDecimal.ZERO);
			saleItem.setDeliveryStatus();

			saleItem = this.saleItemPort.createSaleItem(userContext, saleItem);
		}

		this.paymentUseCase.debitTransaction(userContext, sale.getUnit().get().getUuid());

		return sale;
	}

	private void verifyPendingInventory(final Sale sale) throws BusinessException {
		Inventory inventory = null;

		try {
			inventory = this.inventoryPort.fetchInventoryByGroceryAndStatus(sale.getUnit().get(), InventoryStatus.PENDING);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}

		if (inventory != null) {
			throw new BusinessException("cannot.registe.sale.with.pendind.inventory");
		}
	}
}
