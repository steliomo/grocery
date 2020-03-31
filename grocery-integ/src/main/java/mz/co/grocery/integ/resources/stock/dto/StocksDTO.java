/**
 *
 */
package mz.co.grocery.integ.resources.stock.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.co.grocery.integ.resources.stock.StockQuantity;

/**
 * @author Stélio Moiane
 *
 */
public class StocksDTO {

	private final List<StockDTO> stocksDTO;

	private final Long totalItems;

	private List<StockQuantity> stockQuantities;

	public StocksDTO(final List<StockDTO> stocksDTO, final Long totalItems) {
		this.stocksDTO = stocksDTO;
		this.totalItems = totalItems;
	}

	public List<StockDTO> getStocksDTO() {
		if (this.stocksDTO == null) {
			return new ArrayList<>();
		}

		return Collections.unmodifiableList(this.stocksDTO);
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
