/**
 *
 */
package mz.co.grocery.persistence.inventory;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.inventory.out.StockInventoryPort;
import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.StockInventory;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.StockInventoryTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class StockInventoryPortTest extends AbstractIntegServiceTest {

	@Inject
	private InventoryPort inventoryPort;

	@Inject
	private StockPort stockPort;

	@Inject
	private StockInventoryPort stockInventoryPort;

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private UnitPort unitPort;

	private StockInventory stockInventory;

	@Before
	public void setup() throws BusinessException {
		this.stockInventory = EntityFactory.gimme(StockInventory.class, StockInventoryTemplate.VALID);
		Inventory inventory = this.stockInventory.getInventory().get();

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), inventory.getUnit().get());
		inventory.setUnit(unit);
		inventory = this.inventoryPort.createInventory(this.getUserContext(), inventory);

		Stock stock = this.stockInventory.getStock().get();
		stock.setUnit(this.stockInventory.getInventory().get().getUnit().get());

		ProductDescription productDescription = stock.getProductDescription().get();

		final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());
		productDescription.setProduct(product);

		final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(),
				productDescription.getProductUnit().get());

		productDescription.setProductUnit(productUnit);

		productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(), productDescription);

		stock.setProductDescription(productDescription);
		stock.setStockStatus();
		stock = this.stockPort.createStock(this.getUserContext(), stock);

		this.stockInventory.setStock(stock);
		this.stockInventory.setInventory(inventory);
	}

	@Test
	public void shouldCreateStockInventory() throws BusinessException {
		this.stockInventoryPort.createStockInventory(this.getUserContext(), this.stockInventory);

		Assert.assertNotNull(this.stockInventory.getInventory());
		Assert.assertNotNull(this.stockInventory.getStock());
	}

	@Test
	public void shouldUpdateStockInventory() throws BusinessException {
		final StockInventory stockInventory = this.stockInventoryPort.createStockInventory(this.getUserContext(), this.stockInventory);
		this.stockInventoryPort.updateStockInventory(this.getUserContext(), stockInventory);

		Assert.assertNotNull(this.stockInventory.getInventory());
		Assert.assertNotNull(this.stockInventory.getStock());
	}

}
