/**
 *
 */
package mz.co.grocery.core.domain.inventory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class Inventory extends Domain {

	private Unit unit;

	private LocalDate inventoryDate;

	private InventoryStatus inventoryStatus;

	private Set<StockInventory> stockInventories;

	public Inventory() {
		this.inventoryStatus = InventoryStatus.PENDING;
		this.stockInventories = new HashSet<>();
	}

	public Set<StockInventory> getStockInventories() {
		return Collections.unmodifiableSet(this.stockInventories);
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public LocalDate getInventoryDate() {
		return this.inventoryDate;
	}

	public void setInventoryDate(final LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public InventoryStatus getInventoryStatus() {
		return this.inventoryStatus;
	}

	public void setInventoryStatus(final InventoryStatus inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public void approveInventory() {
		this.inventoryStatus = InventoryStatus.APPROVED;
	}

	public void addStockInventory(final StockInventory stockInventory) {
		this.stockInventories.add(stockInventory);
	}
}
