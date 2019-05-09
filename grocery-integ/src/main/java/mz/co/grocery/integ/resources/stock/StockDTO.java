/**
 *
 */
package mz.co.grocery.integ.resources.stock;

import java.util.Collections;
import java.util.List;

import mz.co.grocery.core.stock.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class StockDTO {

	private final List<Stock> stocks;
	private final Long totalItems;

	public StockDTO(final List<Stock> stocks, final Long totalItems) {
		this.stocks = stocks;
		this.totalItems = totalItems;
	}

	public List<Stock> getStocks() {
		return Collections.unmodifiableList(this.stocks);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
