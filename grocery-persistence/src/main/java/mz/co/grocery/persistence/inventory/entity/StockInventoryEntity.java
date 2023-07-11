/**
 *
 */
package mz.co.grocery.persistence.inventory.entity;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@Entity
@Table(name = "STOCK_INVENTORIES")
public class StockInventoryEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVENTORY_ID", nullable = false)
	private InventoryEntity inventory;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID", nullable = false)
	private StockEntity stock;

	@NotNull
	@Column(name = "FISICAL_INVENTORY", nullable = false)
	private BigDecimal fisicalInventory;

	public Optional<InventoryEntity> getInventory() {
		return Optional.ofNullable(this.inventory);
	}

	public void setInventory(final InventoryEntity inventory) {
		this.inventory = inventory;
	}

	public Optional<StockEntity> getStock() {
		return Optional.ofNullable(this.stock);
	}

	public void setStock(final StockEntity stock) {
		this.stock = stock;
	}

	public BigDecimal getFisicalInventory() {
		return this.fisicalInventory;
	}

	public void setFisicalInventory(final BigDecimal fisicalInventory) {
		this.fisicalInventory = fisicalInventory;
	}
}
