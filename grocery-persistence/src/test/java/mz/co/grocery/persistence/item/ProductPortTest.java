/**
 *
 */
package mz.co.grocery.persistence.item;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ProductTemplate;
import mz.co.grocery.persistence.fixturefactory.StockTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductPortTest extends AbstractIntegServiceTest {

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private StockPort stockPort;

	@Inject
	private UnitPort unitPort;

	private List<Product> products;

	private Unit unit;

	@Before
	public void before() {
		this.products = EntityFactory.gimme(Product.class, 10, ProductTemplate.VALID);

		this.products.forEach(product -> {
			try {
				this.productPort.createProduct(this.getUserContext(), product);
			} catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFindAllProducts() throws BusinessException {
		final List<Product> products = this.productPort.findAllProducts();
		Assert.assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductByName() throws BusinessException {

		final List<Product> products = this.productPort.findProductByName(this.products.get(0).getName());

		Assert.assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductByGrocery() throws BusinessException {

		final List<Stock> stocks = EntityFactory.gimme(Stock.class, 10, StockTemplate.VALID);
		this.unit = this.unitPort.createUnit(this.getUserContext(),
				EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		stocks.forEach(stock -> {
			this.createStock(stock);
		});

		final List<Product> products = this.productPort.findProductsByGrocery(this.unit);

		Assert.assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductsNotInThisGrocery() throws BusinessException {

		Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit = this.unitPort.createUnit(this.getUserContext(), unit);

		final List<Stock> stocks = EntityFactory.gimme(Stock.class, 10, StockTemplate.VALID);
		this.unit = this.unitPort.createUnit(this.getUserContext(),
				EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		stocks.forEach(stock -> {
			this.createStock(stock);
		});

		final List<Product> products = this.productPort.findProductsNotInThisGrocery(unit);

		Assert.assertFalse(products.isEmpty());

		for (final Stock stock : stocks) {
			Assert.assertNotEquals(unit.getUuid(), stock.getUnit().get().getUuid());
		}
	}

	private void createStock(Stock stock) {
		try {
			ProductDescription productDescription = stock.getProductDescription().get();
			final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());
			final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(),
					productDescription.getProductUnit().get());

			productDescription.setProduct(product);
			productDescription.setProductUnit(productUnit);

			productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(),
					productDescription);

			stock.setUnit(this.unit);
			stock.setProductDescription(productDescription);
			stock.setStockStatus();

			stock = this.stockPort.createStock(this.getUserContext(), stock);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}
}
