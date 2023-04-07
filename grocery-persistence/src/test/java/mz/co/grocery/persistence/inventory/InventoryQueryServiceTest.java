/**
 *
 */
package mz.co.grocery.persistence.inventory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.inventory.service.InventoryQueryService;
import mz.co.grocery.core.inventory.service.InventoryService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.InventoryTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private InventoryService inventoryService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private InventoryQueryService inventoryQueryService;

	private Grocery grocery;

	private Inventory inventory;

	@Before
	public void setup() throws BusinessException {
		this.inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID);
		this.grocery = this.groceryService.createGrocery(this.getUserContext(), this.inventory.getGrocery());
		this.inventoryService.createInventory(this.getUserContext(), this.inventory);
	}

	@Test
	public void shouldFetchInventoryByGroceryAndStatus() throws BusinessException {

		final Inventory inventory = this.inventoryQueryService.fetchInventoryByGroceryAndStatus(this.grocery,
		        InventoryStatus.PENDING);

		assertNotNull(inventory);
		assertNotNull(inventory.getGrocery());
		assertTrue(inventory.getStockInventories().isEmpty());
	}

	@Test
	public void shouldFetchInventoryByUuid() throws BusinessException {

		final Inventory inventory = this.inventoryQueryService.fetchInventoryUuid(this.inventory.getUuid());

		assertNotNull(inventory);
		assertNotNull(inventory.getGrocery());
		assertTrue(inventory.getStockInventories().isEmpty());
	}
}
