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
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.service.StockService;
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
			throw new BusinessException("Cannot perform inventory without stock items...");
		}

		try {
			final Inventory foundInventory = this.inventoryQueryService
			        .fetchInventoryByGroceryAndStatus(inventory.getGrocery(), InventoryStatus.PENDING);

			this.updateInventory(userContext, foundInventory);

			inventory.getStockInventories().forEach(stockInventory -> {

				final Optional<StockInventory> optional = foundInventory.getStockInventories().stream()
				        .filter(foundStockInventory -> foundStockInventory.getStock().getUuid()
				                .equals(stockInventory.getStock().getUuid()))
				        .findFirst();

				if (optional.isPresent()) {

					final StockInventory activeStockInventory = optional.get();
					activeStockInventory.setFisicalInventory(stockInventory.getFisicalInventory());
					this.updateStockInventory(userContext, activeStockInventory);

					return;
				}

				this.createStockInventory(userContext, foundInventory, stockInventory);
			});

			return foundInventory;
		}
		catch (final BusinessException exception) {
			this.createInventory(userContext, inventory);

			inventory.getStockInventories().forEach(stockInventory -> {
				this.createStockInventory(userContext, inventory, stockInventory);
			});

			return inventory;
		}
	}

	private void updateStockInventory(final UserContext userContext, final StockInventory stockInventory) {
		try {
			this.stockInventoryService.updateStockInventory(userContext, stockInventory);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	private void createStockInventory(final UserContext userContext, final Inventory inventory,
	        final StockInventory stockInventory) {
		try {
			stockInventory.setInventory(inventory);
			this.stockInventoryService.createStockInventory(userContext, stockInventory);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Inventory approveInventory(final UserContext userContext, final Inventory inventory)
	        throws BusinessException {

		if (InventoryStatus.APPROVED == inventory.getInventoryStatus()) {
			throw new BusinessException("Cannot perform this operation on approved inventories...");
		}

		inventory.approveInventory();

		inventory.getStockInventories().forEach(stockInventory -> {
			final Stock stock = stockInventory.getStock();
			stock.setQuantity(stockInventory.getFisicalInventory());

			this.updateStock(userContext, stock);
		});

		return inventory;
	}

	private void updateStock(final UserContext userContext, final Stock stock) {
		try {
			this.stockService.updateStock(userContext, stock);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Inventory updateInventory(final UserContext userContext, final Inventory inventory)
	        throws BusinessException {
		this.inventoryDAO.update(userContext, inventory);
		return inventory;
	}
}
