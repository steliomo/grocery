/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class StocksDTO {

	private final List<Stock> stocks;

	private final Long totalItems;

	private List<StockQuantity> stockQuantities;

	private DTOMapper<StockDTO, Stock> stockMapper;

	public StocksDTO(final List<Stock> stocks, final Long totalItems, final DTOMapper<StockDTO, Stock> stockMapper) {
		this.stocks = stocks;
		this.totalItems = totalItems;
		this.stockMapper = stockMapper;
	}

	public List<StockDTO> getStocksDTO() {
		return this.stocks.stream().map(stock -> this.stockMapper.toDTO(stock)).collect(Collectors.toList());
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
