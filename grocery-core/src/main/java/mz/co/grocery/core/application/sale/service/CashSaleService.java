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
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase(CashSaleService.NAME)
public class CashSaleService extends AbstractService implements SaleUseCase {

	public static final String NAME = "mz.co.grocery.core.application.sale.service";

	private SalePort salePort;
	private StockPort stockPort;
	private SaleItemPort saleItemPort;
	private PaymentUseCase paymentUseCase;
	private InventoryPort inventoryPort;

	public CashSaleService(final SalePort salePort, final StockPort stockPort, final SaleItemPort saleItemPort,
			final PaymentUseCase paymentUseCase, final InventoryPort inventoryPort) {
		this.salePort = salePort;
		this.stockPort = stockPort;
		this.saleItemPort = saleItemPort;
		this.paymentUseCase = paymentUseCase;
		this.inventoryPort = inventoryPort;
	}

	@Override
	public Sale registSale(final UserContext context, Sale sale) throws BusinessException {
		this.verifyPendingInventory(sale);

		final Set<SaleItem> items = sale.getItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("cannot.create.sale.without.items");
		}

		sale.setSaleStatus(SaleStatus.CLOSED);
		sale.setDeliveryStatus(DeliveryStatus.NA);
		sale.calculateTotal();
		sale.calculateBilling();

		sale = this.salePort.createSale(context, sale);

		for (SaleItem saleItem : items) {

			if (saleItem.isProduct()) {
				final Stock stock = this.stockPort.findStockByUuid(saleItem.getStock().get().getUuid());
				stock.updateStock(saleItem);
				this.stockPort.updateStock(context, stock);
			}

			saleItem.setSale(sale);
			saleItem.setDeliveredQuantity(BigDecimal.ZERO);
			saleItem.setDeliveryStatus(DeliveryStatus.NA);

			saleItem = this.saleItemPort.createSaleItem(context, saleItem);
		}

		this.paymentUseCase.debitTransaction(context, sale.getUnit().get().getUuid());

		return sale;
	}

	private void verifyPendingInventory(final Sale sale) throws BusinessException {
		Inventory inventory = null;

		try {
			inventory = this.inventoryPort.fetchInventoryByGroceryAndStatus(sale.getUnit().get(), InventoryStatus.PENDING);
		} catch (final BusinessException e) {
		}

		if (inventory != null) {
			throw new BusinessException("cannot.registe.sale.with.pendind.inventory");
		}
	}
}