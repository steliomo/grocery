/**
 *
 */
package mz.co.grocery.core.inventory.unit;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.inventory.service.InventoryQueryService;
import mz.co.grocery.core.inventory.service.InventoryService;
import mz.co.grocery.core.inventory.service.InventoryServiceImpl;
import mz.co.grocery.core.inventory.service.StockInventoryService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final InventoryService inventoryService = new InventoryServiceImpl();

	@Mock
	private InventoryDAO inventoryDAO;

	@Mock
	private StockInventoryService stockInventoryService;

	@Mock
	private StockService stockService;

	@Mock
	private InventoryQueryService inventoryQueryService;

	@Mock
	private ApplicationTranslator translator;

	@Test
	public void shouldPerformInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS);

		final UserContext userContext = this.getUserContext();

		Mockito.when(this.inventoryQueryService.fetchInventoryByGroceryAndStatus(inventory.getGrocery(),
				InventoryStatus.PENDING)).thenThrow(new BusinessException());

		this.inventoryService.performInventory(userContext, inventory);

		Mockito.verify(this.inventoryDAO).create(userContext, inventory);

		Assert.assertEquals(InventoryStatus.PENDING, inventory.getInventoryStatus());
		inventory.getStockInventories().forEach(stockInventory -> {
			Assert.assertEquals(inventory.getInventoryDate(), stockInventory.getInventory().getInventoryDate());
		});

	}

	@Test(expected = BusinessException.class)
	public void shouldNotPerformInventoryWithoutStockInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID);
		this.inventoryService.performInventory(this.getUserContext(), inventory);

	}

	@Test
	public void shouldPerformInventoryUpdate() throws BusinessException {
		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS_WITH_UUID);

		final UserContext userContext = this.getUserContext();

		Mockito.when(this.inventoryQueryService.fetchInventoryByGroceryAndStatus(inventory.getGrocery(),
				InventoryStatus.PENDING)).thenReturn(inventory);

		this.inventoryService.performInventory(userContext, inventory);

		Mockito.verify(this.inventoryDAO).update(userContext, inventory);

		Assert.assertEquals(InventoryStatus.PENDING, inventory.getInventoryStatus());
		inventory.getStockInventories().forEach(stockInventory -> {
			Assert.assertEquals(inventory.getInventoryDate(), stockInventory.getInventory().getInventoryDate());
		});
	}

	@Test(expected = BusinessException.class)
	public void shouldNotApproveInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS);
		inventory.approveInventory();
		Mockito.when(this.inventoryQueryService.fetchInventoryUuid(inventory.getUuid())).thenReturn(inventory);
		this.inventoryService.approveInventory(this.getUserContext(), inventory.getUuid());
	}

	@Test
	public void shouldApproveInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS);

		Mockito.when(this.inventoryQueryService.fetchInventoryUuid(inventory.getUuid())).thenReturn(inventory);

		this.inventoryService.approveInventory(this.getUserContext(), inventory.getUuid());

		Assert.assertEquals(InventoryStatus.APPROVED, inventory.getInventoryStatus());
	}
}
