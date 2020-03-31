/**
 *
 */
package mz.co.grocery.integ.resources.stock;

import java.math.BigDecimal;

import mz.co.grocery.core.stock.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class StockQuantity {

	private Stock stock;
	private BigDecimal quantity;

	public Stock getStock() {
		return this.stock;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}
}
