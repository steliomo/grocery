/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class InventoryDTOMapper extends AbstractDTOMapper<InventoryDTO, Inventory> implements DTOMapper<InventoryDTO, Inventory> {

	private DTOMapper<UnitDTO, Unit> unitMapper;

	private DTOMapper<StockInventoryDTO, StockInventory> stockInventoryMapper;

	public InventoryDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper) {
		this.unitMapper = unitMapper;
	}

	@Override
	public InventoryDTO toDTO(final Inventory domain) {
		final InventoryDTO dto = new InventoryDTO();

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));

		dto.setInventoryDate(domain.getInventoryDate());
		dto.setInventoryStatus(domain.getInventoryStatus());

		domain.getStockInventories().forEach(stockInventory -> dto.addStockInventoryDTO(this.stockInventoryMapper.toDTO(stockInventory)));

		return this.toDTO(dto, domain);
	}

	@Override
	public Inventory toDomain(final InventoryDTO dto) {
		final Inventory domain = new Inventory();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));

		domain.setInventoryDate(dto.getInventoryDate());
		domain.setInventoryStatus(dto.getInventoryStatus());

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setStockInventoryMapper(final DTOMapper<StockInventoryDTO, StockInventory> stockInventoryMapper) {
		this.stockInventoryMapper = stockInventoryMapper;
	}
}
