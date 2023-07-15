/**
 *
 */
package mz.co.grocery.core.application.inventory.service;

import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.application.inventory.in.PerformInventoroyUseCase;
import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.inventory.out.StockInventoryPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class PerformInventoryService extends AbstractService implements PerformInventoroyUseCase {

	private InventoryPort inventoryPort;

	private StockInventoryPort stockInventoryPort;

	public PerformInventoryService(final InventoryPort inventoryPort, final StockInventoryPort stockInventoryPort) {
		this.inventoryPort = inventoryPort;
		this.stockInventoryPort = stockInventoryPort;
	}

	@Override
	public Inventory performInventory(final UserContext userContext, Inventory inventory)
			throws BusinessException {

		if (inventory.getStockInventories().isEmpty()) {
			throw new BusinessException("cannot.perform.inventory.without.stock.items");
		}

		try {
			final Inventory foundInventory = this.inventoryPort.fetchInventoryByGroceryAndStatus(inventory.getUnit().get(), InventoryStatus.PENDING);

			this.inventoryPort.updateInventory(userContext, foundInventory);

			for (final StockInventory stockInventory : inventory.getStockInventories()) {
				final Optional<StockInventory> optional = foundInventory.getStockInventories().stream()
						.filter(foundStockInventory -> foundStockInventory.getStock().get().getUuid()
								.equals(stockInventory.getStock().get().getUuid()))
						.findFirst();

				if (optional.isPresent()) {
					final StockInventory activeStockInventory = optional.get();

					activeStockInventory.setInventory(foundInventory);
					activeStockInventory.setFisicalInventory(stockInventory.getFisicalInventory());

					this.stockInventoryPort.updateStockInventory(userContext, activeStockInventory);
				} else {
					stockInventory.setInventory(foundInventory);

					this.stockInventoryPort.createStockInventory(userContext, stockInventory);
				}
			}

			return foundInventory;

		} catch (final BusinessException exception) {
			final Set<StockInventory> stockInventories = inventory.getStockInventories();

			inventory = this.inventoryPort.createInventory(userContext, inventory);

			for (final StockInventory stockInventory : stockInventories) {
				stockInventory.setInventory(inventory);

				this.stockInventoryPort.createStockInventory(userContext, stockInventory);
			}

			return inventory;
		}
	}
}
