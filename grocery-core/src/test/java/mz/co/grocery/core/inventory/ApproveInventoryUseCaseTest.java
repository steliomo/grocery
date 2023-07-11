/**
 *
 */
package mz.co.grocery.core.inventory;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.inventory.in.ApproveInventoryUseCase;
import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.inventory.out.StockInventoryPort;
import mz.co.grocery.core.application.inventory.service.ApproveInventoryService;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ApproveInventoryUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private InventoryPort inventoryPort;

	@Mock
	private StockPort stockPort;

	@Mock
	private StockInventoryPort stockInventoryService;

	@Mock
	private ApplicationTranslator translator;

	@InjectMocks
	private final ApproveInventoryUseCase approveInventoryUseCase = new ApproveInventoryService(this.inventoryPort, this.stockPort);

	@Test(expected = BusinessException.class)
	public void shouldNotApproveInventory() throws BusinessException {
		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS);
		inventory.approveInventory();
		Mockito.when(this.inventoryPort.fetchInventoryUuid(inventory.getUuid())).thenReturn(inventory);
		this.approveInventoryUseCase.approveInventory(this.getUserContext(), inventory.getUuid());
	}

	@Test
	public void shouldApproveInventory() throws BusinessException {

		final Inventory inventory = EntityFactory.gimme(Inventory.class, InventoryTemplate.WITH_STOCKS);

		Mockito.when(this.inventoryPort.fetchInventoryUuid(inventory.getUuid())).thenReturn(inventory);

		this.approveInventoryUseCase.approveInventory(this.getUserContext(), inventory.getUuid());

		Assert.assertEquals(InventoryStatus.APPROVED, inventory.getInventoryStatus());
	}
}
