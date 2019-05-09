/**
 *
 */
package mz.co.grocery.core.stock.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.StockTemplate;
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
public class StockServiceTest extends AbstractIntegServiceTest {

	@Inject
	private StockService stockService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	private Stock stock;

	@Before
	public void before() throws BusinessException {

		this.stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		this.productService.createProduct(this.getUserContext(), this.stock.getProductDescription().getProduct());
		this.productSizeService.createProductUnit(this.getUserContext(),
		        this.stock.getProductDescription().getProductUnit());
		this.productDescriptionService.createProductDescription(this.getUserContext(),
		        this.stock.getProductDescription());
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
