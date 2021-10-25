/**
 *
 */
package mz.co.grocery.core.inventory.service;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(InventoryServiceImpl.NAME)
public class InventoryServiceImpl extends AbstractService implements InventoryService {

	public static final String NAME = "mz.co.grocery.core.inventory.service.InventoryServiceImpl";

	@Inject
	private InventoryDAO inventoryDAO;

	@Inject
	private StockInventoryService stockInventoryService;

	@Inject
	private StockService stockService;

	@Inject
	private InventoryQueryService inventoryQueryService;

	@Inject
	private ApplicationTranslator translator;

	@Override
	public Inventory createInventory(final UserContext userContext, final Inventory inventory)
			throws BusinessException {
		this.inventoryDAO.create(userContext, inventory);
		return inventory;
	}

	@Override
	public Inventory performInventory(final UserContext userContext, final Inventory inventory)
			throws BusinessException {

		if (inventory.getStockInventories().isEmpty()) {
			throw new BusinessException(this.translator.getTranslation("cannot.perform.inventory.without.stock.items"));
		}

		try {
			final Inventory foundInventory = this.inventoryQueryService
					.fetchInventoryByGroceryAndStatus(inventory.getGrocery(), InventoryStatus.PENDING);

			this.updateInventory(userContext, foundInventory);

			for (final StockInventory stockInventory : inventory.getStockInventories()) {

				final Optional<StockInventory> optional = foundInventory.getStockInventories().stream()
						.filter(foundStockInventory -> foundStockInventory.getStock().getUuid()
								.equals(stockInventory.getStock().getUuid()))
						.findFirst();

				if (optional.isPresent()) {
					final StockInventory activeStockInventory = optional.get();
					activeStockInventory.setFisicalInventory(stockInventory.getFisicalInventory());

					this.stockInventoryService.updateStockInventory(userContext, activeStockInventory);
				} else {
					stockInventory.setInventory(foundInventory);

					this.stockInventoryService.createStockInventory(userContext, stockInventory);
				}
			}

			return foundInventory;

		} catch (final BusinessException exception) {
			this.createInventory(userContext, inventory);

			for (final StockInventory stockInventory : inventory.getStockInventories()) {

				stockInventory.setInventory(inventory);
				this.stockInventoryService.createStockInventory(userContext, stockInventory);
			}

			return inventory;
		}
	}

	@Override
	public Inventory approveInventory(final UserContext userContext, final String inventoryUuid)
			throws BusinessException {

		final Inventory inventory = this.inventoryQueryService.fetchInventoryUuid(inventoryUuid);

		if (InventoryStatus.APPROVED == inventory.getInventoryStatus()) {
			throw new BusinessException(this.translator.getTranslation("cannot.perform.aproval.for.approved.inventory"));
		}

		inventory.approveInventory();
		this.updateInventory(userContext, inventory);

		for (final StockInventory stockInventory : inventory.getStockInventories()) {
			final Stock stock = stockInventory.getStock();
			stock.setQuantity(stockInventory.getFisicalInventory());

			this.stockService.updateStock(userContext, stock);
		}

		return inventory;
	}

	@Override
	public Inventory updateInventory(final UserContext userContext, final Inventory inventory)
			throws BusinessException {
		this.inventoryDAO.update(userContext, inventory);
		return inventory;
	}
}
