/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import static mz.co.grocery.integ.resources.util.ProxyUtil.isInitialized;

import java.math.BigDecimal;

import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.stock.dto.StockDTO;;

/**
 * @author Stélio Moiane
 *
 */
public class StockInventoryDTO extends GenericDTO<StockInventory> {

	private StockDTO stockDTO;

	private String fisicalInventory;

	public StockInventoryDTO() {
	}

	public StockInventoryDTO(final StockInventory stockInventory) {
		super(stockInventory);
		this.mapper(stockInventory);
	}

	@Override
	public void mapper(final StockInventory stockInventory) {

		final Stock stock = stockInventory.getStock();

		if (isInitialized(stock)) {
			this.stockDTO = new StockDTO(stock);
		}

		this.fisicalInventory = String.valueOf(stockInventory.getFisicalInventory());
	}

	@Override
	public StockInventory get() {
		final StockInventory stockInventory = this.get(new StockInventory());
		stockInventory.setStock(this.stockDTO.get());
		stockInventory.setFisicalInventory(new BigDecimal(this.fisicalInventory));

		return stockInventory;
	}

	public StockDTO getStockDTO() {
		return this.stockDTO;
	}

	public String getFisicalInventory() {
		return this.fisicalInventory;
	}
}
