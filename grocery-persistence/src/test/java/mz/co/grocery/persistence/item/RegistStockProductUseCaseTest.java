/**
 *
 */
package mz.co.grocery.persistence.item;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.item.in.RegistStockProductUseCase;
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
import mz.co.grocery.persistence.fixturefactory.StockTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RegistStockProductUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private UnitPort unitPort;

	@Inject
	private RegistStockProductUseCase registStockProductUseCase;

	@Inject
	private StockPort stockPort;

	@Test
	public void shouldRegistStockProduct() throws BusinessException {

		Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);

		ProductDescription productDescription = stock.getProductDescription().get();

		unit = this.unitPort.createUnit(this.getUserContext(), unit);

		final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());

		final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(),
				productDescription.getProductUnit().get());

		productDescription.setProduct(product);
		productDescription.setProductUnit(productUnit);

		productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(),
				productDescription);

		stock.setUnit(unit);
		stock.setProductDescription(productDescription);
		stock.setStockStatus();

		final Stock registedStock = this.registStockProductUseCase.regist(this.getUserContext(), stock);

		Assert.assertNotNull(registedStock.getId());
		Assert.assertEquals(unit.getUuid(), registedStock.getUnit().get().getUuid());
		Assert.assertEquals(productDescription.getUuid(), registedStock.getProductDescription().get().getUuid());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistDuplicatedStockProduct() throws BusinessException {

		Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);

		ProductDescription productDescription = stock.getProductDescription().get();

		unit = this.unitPort.createUnit(this.getUserContext(), unit);

		final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());

		final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(),
				productDescription.getProductUnit().get());

		productDescription.setProduct(product);
		productDescription.setProductUnit(productUnit);

		productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(),
				productDescription);

		stock.setUnit(unit);
		stock.setProductDescription(productDescription);
		stock.setStockStatus();

		stock = this.stockPort.createStock(this.getUserContext(), stock);
		stock.setId(null);
		stock.setCreatedAt(null);
		stock.setCreatedBy(null);

		this.registStockProductUseCase.regist(this.getUserContext(), stock);
	}
}
