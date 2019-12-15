/**
 *
 */
package mz.co.grocery.core.inventory.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
        @NamedQuery(name = InventoryDAO.QUERY_NAME.fetchByGroceryAndStatus, query = InventoryDAO.QUERY.fetchByGroceryAndStatus) })
@Entity
@Table(name = "INVENTORIES")
public class Inventory extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private Grocery grocery;

	@NotNull
	@Column(name = "INVENTORY_DATE", nullable = false)
	private LocalDate inventoryDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "INVENTORY_STATUS", nullable = false, length = 15)
	private InventoryStatus inventoryStatus;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory")
	private final Set<StockInventory> stockInventories = new HashSet<>();

	public Set<StockInventory> getStockInventories() {
		return Collections.unmodifiableSet(this.stockInventories);
	}

	public Grocery getGrocery() {
		return this.grocery;
	}

	public void setGrocery(final Grocery grocery) {
		this.grocery = grocery;
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

	public void approveInventory() {
		this.inventoryStatus = InventoryStatus.APPROVED;
	}
}
