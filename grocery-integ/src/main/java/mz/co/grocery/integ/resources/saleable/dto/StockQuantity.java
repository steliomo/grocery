/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.domain.sale.Stock;

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
