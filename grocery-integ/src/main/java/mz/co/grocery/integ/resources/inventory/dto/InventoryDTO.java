/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryDTO extends GenericDTO<Inventory> {

	private GroceryDTO groceryDTO;

	private LocalDate InventoryDate;

	private InventoryStatus inventoryStatus;

	private Set<StockInventoryDTO> stockInventoryDTOs;

	public InventoryDTO(final Inventory inventory) {
		super(inventory);
		this.mapper(inventory);
	}

	@Override
	public void mapper(final Inventory inventory) {
		this.groceryDTO = new GroceryDTO(inventory.getGrocery());

		this.InventoryDate = inventory.getInventoryDate();

		this.inventoryStatus = inventory.getInventoryStatus();

		this.stockInventoryDTOs = inventory.getStockInventories().stream().map(stockInventory -> {
			return new StockInventoryDTO(stockInventory);
		}).collect(Collectors.toSet());
	}

	public GroceryDTO getGroceryDTO() {
		return this.groceryDTO;
	}

	public LocalDate getInventoryDate() {
		return this.InventoryDate;
	}

	public InventoryStatus getInventoryStatus() {
		return this.inventoryStatus;
	}

	public Set<StockInventoryDTO> getStockInvetoryDTOs() {
		return this.stockInventoryDTOs;
	}
}
