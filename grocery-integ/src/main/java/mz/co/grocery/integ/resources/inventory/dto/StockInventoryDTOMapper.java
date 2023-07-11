/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class StockInventoryDTOMapper extends AbstractDTOMapper<StockInventoryDTO, StockInventory>
implements DTOMapper<StockInventoryDTO, StockInventory> {

	private DTOMapper<StockDTO, Stock> stockMapper;

	public StockInventoryDTOMapper(final DTOMapper<StockDTO, Stock> stockMapper) {
		this.stockMapper = stockMapper;
	}

	@Override
	public StockInventoryDTO toDTO(final StockInventory domain) {
		final StockInventoryDTO dto = new StockInventoryDTO();

		domain.getStock().ifPresent(stock -> dto.setStockDTO(this.stockMapper.toDTO(stock)));
		dto.setFisicalInventory(domain.getFisicalInventory());

		return this.toDTO(dto, domain);
	}

	@Override
	public StockInventory toDomain(final StockInventoryDTO dto) {
		final StockInventory domain = new StockInventory();

		dto.getStockDTO().ifPresent(stock -> domain.setStock(this.stockMapper.toDomain(stock)));
		domain.setFisicalInventory(dto.getFisicalInventory());

		return this.toDomain(dto, domain);
	}
}
