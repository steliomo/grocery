/**
 *
 */
package mz.co.grocery.core.application.inventory.service;

import mz.co.grocery.core.application.inventory.in.ApproveInventoryUseCase;
import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class ApproveInventoryService extends AbstractService implements ApproveInventoryUseCase {

	private InventoryPort inventoryPort;

	private StockPort stockPort;

	public ApproveInventoryService(final InventoryPort inventoryPort, final StockPort stockPort) {
		this.inventoryPort = inventoryPort;
		this.stockPort = stockPort;
	}

	@Override
	public Inventory approveInventory(final UserContext userContext, final String inventoryUuid)
			throws BusinessException {

		final Inventory inventory = this.inventoryPort.fetchInventoryUuid(inventoryUuid);

		if (InventoryStatus.APPROVED == inventory.getInventoryStatus()) {
			throw new BusinessException("cannot.perform.aproval.for.approved.inventory");
		}

		inventory.approveInventory();
		this.inventoryPort.updateInventory(userContext, inventory);

		for (final StockInventory stockInventory : inventory.getStockInventories()) {
			final Stock stock = stockInventory.getStock().get();
			stock.setInventoryDate(inventory.getInventoryDate());
			stock.setInventoryQuantity(stockInventory.getFisicalInventory());
			stock.adjustNegativeQuantity();
			stock.setProductStockStatus();

			this.stockPort.updateStock(userContext, stock);
		}

		return inventory;
	}
}
