/**
 *
 */
package mz.co.grocery.persistence.inventory.entity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.persistence.inventory.repository.InventoryRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;
import mz.co.msaude.boot.frameworks.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = InventoryRepository.QUERY_NAME.fetchByGroceryAndStatus, query = InventoryRepository.QUERY.fetchByGroceryAndStatus),
	@NamedQuery(name = InventoryRepository.QUERY_NAME.fetchByUuid, query = InventoryRepository.QUERY.fetchByUuid) })
@Entity
@Table(name = "INVENTORIES")
public class InventoryEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private UnitEntity unit;

	@NotNull
	@Column(name = "INVENTORY_DATE", nullable = false)
	private LocalDate inventoryDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "INVENTORY_STATUS", nullable = false, length = 15)
	private InventoryStatus inventoryStatus;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory")
	private Set<StockInventoryEntity> stockInventories;

	public Optional<UnitEntity> getUnit() {
		if (ProxyUtil.isProxy(this.unit)) {
			final UnitEntity unit = new UnitEntity();
			unit.setId(ProxyUtil.getIdentifier(this.unit));

			return Optional.of(unit);
		}

		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
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

	public Optional<Set<StockInventoryEntity>> getStockInventories() {
		return Optional.ofNullable(this.stockInventories);
	}
}
