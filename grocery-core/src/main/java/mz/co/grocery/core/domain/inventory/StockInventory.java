/**
 *
 */
package mz.co.grocery.core.domain.inventory;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.sale.Stock;

/**
 * @author Stélio Moiane
 *
 */

public class StockInventory extends Domain {

	private Inventory inventory;

	private Stock stock;

	private BigDecimal fisicalInventory;

	public Optional<Inventory> getInventory() {
		return Optional.ofNullable(this.inventory);
	}

	public void setInventory(final Inventory inventory) {
		this.inventory = inventory;
	}

	public Optional<Stock> getStock() {
		return Optional.ofNullable(this.stock);
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
