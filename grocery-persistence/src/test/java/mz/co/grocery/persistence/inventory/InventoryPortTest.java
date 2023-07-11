/**
 *
 */
package mz.co.grocery.persistence.inventory;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.InventoryTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryPortTest extends AbstractIntegServiceTest {

	@Inject
	private InventoryPort inventoryPort;

	@Inject
	private UnitPort unitPort;

	private Unit unit;

	private Inventory inventory;

	@Before
	public void setup() throws BusinessException {
		this.inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID);
		this.unit = this.unitPort.createUnit(this.getUserContext(), this.inventory.getUnit().get());

		this.inventory.setUnit(this.unit);
		this.inventory = this.inventoryPort.createInventory(this.getUserContext(), this.inventory);
	}

	@Test
	public void shouldFetchInventoryByGroceryAndStatus() throws BusinessException {

		final Inventory inventory = this.inventoryPort.fetchInventoryByGroceryAndStatus(this.unit,
				InventoryStatus.PENDING);

		Assert.assertNotNull(inventory);
		Assert.assertNotNull(inventory.getUnit());
		Assert.assertTrue(inventory.getStockInventories().isEmpty());
	}

	@Test
	public void shouldFetchInventoryByUuid() throws BusinessException {

		final Inventory inventory = this.inventoryPort.fetchInventoryUuid(this.inventory.getUuid());

		Assert.assertNotNull(inventory);
		Assert.assertNotNull(inventory.getUnit());
		Assert.assertTrue(inventory.getStockInventories().isEmpty());
	}
}
