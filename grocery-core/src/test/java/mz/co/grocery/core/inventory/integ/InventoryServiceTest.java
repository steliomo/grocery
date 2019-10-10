/**
 *
 */
package mz.co.grocery.core.inventory.integ;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.inventory.service.InventoryService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private InventoryService inventoryService;

	@Inject
	private GroceryService groceryService;

	private Inventory inventory;

	@Before
	public void setup() throws BusinessException {
		this.inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID);
		this.groceryService.createGrocery(this.getUserContext(), this.inventory.getGrocery());
	}

	@Test
	public void shouldCreateInventory() throws BusinessException {
		this.inventoryService.createInventory(this.getUserContext(), this.inventory);

		TestUtil.assertCreation(this.inventory);
		assertEquals(InventoryStatus.PENDING, this.inventory.getInventoryStatus());
	}

	@Test
	public void shouldCreateUpdate() throws BusinessException {
		this.inventoryService.createInventory(this.getUserContext(), this.inventory);
		this.inventoryService.updateInventory(this.getUserContext(), this.inventory);

		TestUtil.assertUpdate(this.inventory);
		assertEquals(InventoryStatus.PENDING, this.inventory.getInventoryStatus());
	}

}
