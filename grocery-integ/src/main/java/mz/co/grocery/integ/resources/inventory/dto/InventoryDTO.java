/**
 *
 */
package mz.co.grocery.integ.resources.inventory.dto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryDTO extends GenericDTO {

	private UnitDTO unitDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate inventoryDate;

	private InventoryStatus inventoryStatus;

	private Set<StockInventoryDTO> stockInventoriesDTO;

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public LocalDate getInventoryDate() {
		return this.inventoryDate;
	}

	public void setInventoryDate(final LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public InventoryStatus getInventoryStatus() {
		return this.inventoryStatus;
	}

	public void setInventoryStatus(final InventoryStatus inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public Set<StockInventoryDTO> getStockInventoriesDTO() {
		return this.stockInventoriesDTO;
	}

	public void addStockInventoryDTO(final StockInventoryDTO stockInventoryDTO) {
		this.stockInventoriesDTO.add(stockInventoryDTO);
	}
}
