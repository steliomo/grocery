/**
 *
 */
package mz.co.grocery.core.saleable.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class StockServiceTest extends AbstractIntegServiceTest {

	@Inject
	private StockService stockService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private GroceryService groceryService;

	private Stock stock;

	@Before
	public void before() throws BusinessException {

		this.stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		this.productService.createProduct(this.getUserContext(), this.stock.getProductDescription().getProduct());
		this.productSizeService.createProductUnit(this.getUserContext(),
		        this.stock.getProductDescription().getProductUnit());
		this.productDescriptionService.createProductDescription(this.getUserContext(),
		        this.stock.getProductDescription());
		this.groceryService.createGrocery(this.getUserContext(), this.stock.getGrocery());
	}

	@Test
	public void shouldCreateStock() throws BusinessException {

		this.stockService.createStock(this.getUserContext(), this.stock);

		TestUtil.assertCreation(this.stock);
	}

	@Test
	public void shouldUpdateStock() throws BusinessException {

		this.stockService.createStock(this.getUserContext(), this.stock);
		this.stockService.updateStock(this.getUserContext(), this.stock);

		TestUtil.assertUpdate(this.stock);
	}
}
