/**
 *
 */
package mz.co.grocery.integ.resources.stock.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.integ.resources.stock.StockQuantity;

/**
 * @author Stélio Moiane
 *
 */
public class StocksDTO {

	private final List<Stock> stocks;

	private final Long totalItems;

	private List<StockQuantity> stockQuantities;

	public StocksDTO(final List<Stock> stocks, final Long totalItems) {
		this.stocks = stocks;
		this.totalItems = totalItems;
	}

	public List<Stock> getStocks() {
		if (this.stocks == null) {
			return new ArrayList<>();
		}

		return Collections.unmodifiableList(this.stocks);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}

	public List<StockQuantity> getStockQuantities() {
		if (this.stockQuantities == null) {
			return new ArrayList<>();
		}

		return Collections.unmodifiableList(this.stockQuantities);
	}
}
