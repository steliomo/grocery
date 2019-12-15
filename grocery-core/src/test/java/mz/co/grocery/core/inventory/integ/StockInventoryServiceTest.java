/**
 *
 */
package mz.co.grocery.core.inventory.integ;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.StockInventoryTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.grocery.core.inventory.service.InventoryService;
import mz.co.grocery.core.inventory.service.StockInventoryService;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class StockInventoryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private InventoryService inventoryService;

	@Inject
	private StockService stockService;

	@Inject
	private StockInventoryService stockInventoryService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private GroceryService groceryService;

	private StockInventory stockInventory;

	@Before
	public void setup() throws BusinessException {
		this.stockInventory = EntityFactory.gimme(StockInventory.class, StockInventoryTemplate.VALID);
		this.groceryService.createGrocery(this.getUserContext(), this.stockInventory.getInventory().getGrocery());
		this.inventoryService.createInventory(this.getUserContext(), this.stockInventory.getInventory());

		final Stock stock = this.stockInventory.getStock();
		stock.setGrocery(this.stockInventory.getInventory().getGrocery());
		this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());
		this.productSizeService.createProductUnit(this.getUserContext(),
		        stock.getProductDescription().getProductUnit());
		this.productDescriptionService.createProductDescription(this.getUserContext(), stock.getProductDescription());

		this.stockService.createStock(this.getUserContext(), stock);
	}

	@Test
	public void shouldCreateStockInventory() throws BusinessException {
		this.stockInventoryService.createStockInventory(this.getUserContext(), this.stockInventory);

		TestUtil.assertCreation(this.stockInventory);
		assertNotNull(this.stockInventory.getInventory());
		assertNotNull(this.stockInventory.getStock());
	}

	@Test
	public void shouldUpdateStockInventory() throws BusinessException {
		this.stockInventoryService.createStockInventory(this.getUserContext(), this.stockInventory);
		this.stockInventoryService.updateStockInventory(this.getUserContext(), this.stockInventory);

		TestUtil.assertUpdate(this.stockInventory);
		assertNotNull(this.stockInventory.getInventory());
		assertNotNull(this.stockInventory.getStock());
	}

}
