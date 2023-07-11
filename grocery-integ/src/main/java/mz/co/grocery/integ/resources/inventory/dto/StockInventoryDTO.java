/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;;

/**
 * @author Stélio Moiane
 *
 */
public class StockInventoryDTO extends GenericDTO {

	private StockDTO stockDTO;

	private BigDecimal fisicalInventory;

	public Optional<StockDTO> getStockDTO() {
		return Optional.ofNullable(this.stockDTO);
	}

	public void setStockDTO(final StockDTO stockDTO) {
		this.stockDTO = stockDTO;
	}

	public BigDecimal getFisicalInventory() {
		return this.fisicalInventory;
	}

	public void setFisicalInventory(final BigDecimal fisicalInventory) {
		this.fisicalInventory = fisicalInventory;
	}
}
