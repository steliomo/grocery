/**
 *
 */
package mz.co.grocery.core.inventory.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@Entity
@Table(name = "STOCK_INVENTORIES")
public class StockInventory extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVENTORY_ID", nullable = false)
	private Inventory inventory;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID", nullable = false)
	private Stock stock;

	@NotNull
	@Column(name = "FISICAL_INVENTORY", nullable = false)
	private BigDecimal fisicalInventory;

	public Inventory getInventory() {
		return this.inventory;
	}

	public void setInventory(final Inventory inventory) {
		this.inventory = inventory;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(final Stock stock) {
		this.stock = stock;
	}

	public BigDecimal getFisicalInventory() {
		return this.fisicalInventory;
	}

	public void setFisicalInventory(final BigDecimal fisicalInventory) {
		this.fisicalInventory = fisicalInventory;
	}
}
