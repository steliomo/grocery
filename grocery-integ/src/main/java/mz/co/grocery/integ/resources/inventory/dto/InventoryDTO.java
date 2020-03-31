/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryDTO extends GenericDTO<Inventory> {

	private GroceryDTO groceryDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate inventoryDate;

	private InventoryStatus inventoryStatus;

	private Set<StockInventoryDTO> stockInventoriesDTO;

	public InventoryDTO() {
	}

	public InventoryDTO(final Inventory inventory) {
		super(inventory);
		this.mapper(inventory);
	}

	@Override
	public void mapper(final Inventory inventory) {
		if (ProxyUtil.isInitialized(inventory.getGrocery())) {
			this.groceryDTO = new GroceryDTO(inventory.getGrocery());
		}

		this.inventoryDate = inventory.getInventoryDate();

		this.inventoryStatus = inventory.getInventoryStatus();

		this.stockInventoriesDTO = inventory.getStockInventories().stream()
		        .map(stockInventory -> new StockInventoryDTO(stockInventory)).collect(Collectors.toSet());
	}

	@Override
	public Inventory get() {
		final Inventory inventory = this.get(new Inventory());
		inventory.setGrocery(this.groceryDTO.get());
		inventory.setInventoryDate(this.inventoryDate);
		inventory.setInventoryStatus(this.inventoryStatus);

		for (final StockInventoryDTO stockInventoryDTO : this.stockInventoriesDTO) {
			inventory.addStockInventory(stockInventoryDTO.get());
		}

		return inventory;
	}

	public GroceryDTO getGroceryDTO() {
		return this.groceryDTO;
	}

	public LocalDate getInventoryDate() {
		return this.inventoryDate;
	}

	public InventoryStatus getInventoryStatus() {
		return this.inventoryStatus;
	}

	public Set<StockInventoryDTO> getStockInventoriesDTO() {
		return this.stockInventoriesDTO;
	}
}
