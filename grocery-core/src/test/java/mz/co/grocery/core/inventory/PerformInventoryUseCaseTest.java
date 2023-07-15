/**
 *
 */
package mz.co.grocery.core.inventory;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.inventory.in.PerformInventoroyUseCase;
import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.inventory.out.StockInventoryPort;
import mz.co.grocery.core.application.inventory.service.PerformInventoryService;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class PerformInventoryUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private StockInventoryPort stockInventoryPort;

	@Mock
	private StockPort stockPort;

	@Mock
	private InventoryPort inventoryPort;

	@Mock
	private ApplicationTranslator translator;

	@InjectMocks
	private final PerformInventoroyUseCase performInventoroyUseCase = new PerformInventoryService(this.inventoryPort, this.stockInventoryPort);

	@Test
	public void shouldPerformInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS);

		final UserContext userContext = this.getUserContext();

		Mockito.when(this.inventoryPort.fetchInventoryByGroceryAndStatus(inventory.getUnit().get(),
				InventoryStatus.PENDING)).thenThrow(new BusinessException());

		this.performInventoroyUseCase.performInventory(userContext, inventory);

		Mockito.verify(this.inventoryPort).createInventory(userContext, inventory);

		Assert.assertEquals(InventoryStatus.PENDING, inventory.getInventoryStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotPerformInventoryWithoutStockInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID);
		this.performInventoroyUseCase.performInventory(this.getUserContext(), inventory);

	}

	@Test
	public void shouldPerformInventoryUpdate() throws BusinessException {
		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS_WITH_UUID);

		final UserContext userContext = this.getUserContext();

		Mockito.when(this.inventoryPort.fetchInventoryByGroceryAndStatus(inventory.getUnit().get(),
				InventoryStatus.PENDING)).thenReturn(inventory);

		this.performInventoroyUseCase.performInventory(userContext, inventory);

		Mockito.verify(this.inventoryPort).updateInventory(userContext, inventory);

		Assert.assertEquals(InventoryStatus.PENDING, inventory.getInventoryStatus());
		inventory.getStockInventories().forEach(stockInventory -> {
			Assert.assertEquals(inventory.getInventoryDate(), stockInventory.getInventory().get().getInventoryDate());
		});
	}
}
