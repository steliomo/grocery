/**
 *
 */
package mz.co.grocery.persistence.saleable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractServiceTest;
import mz.co.grocery.persistence.fixturefactory.StockTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
@Service
public class StockBuilder extends AbstractServiceTest {

	@Inject
	private ProductPort productService;

	@Inject
	private ProductUnitPort productUnitService;

	@Inject
	private ProductDescriptionPort productDescriptionService;

	@Inject
	private StockPort stockService;

	private List<Stock> stocks;

	private int number;

	private Unit unit;

	public List<Stock> build() {

		return this.stocks;
	}

	public StockBuilder inAnalysis() {
		EntityFactory.gimme(Stock.class, this.number, StockTemplate.IN_ANALYSIS, result -> {
			if (result instanceof Stock) {
				Stock stock = (Stock) result;
				try {
					stock = StockBuilder.this.createStock(stock, StockBuilder.this.unit);

					if (this.stocks == null) {
						this.stocks = new ArrayList<>();
					}

					this.stocks.add(stock);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		return this;
	}

	public StockBuilder valid() {
		EntityFactory.gimme(Stock.class, this.number, StockTemplate.VALID, result -> {
			if (result instanceof Stock) {
				Stock stock = (Stock) result;
				try {
					stock = StockBuilder.this.createStock(stock, StockBuilder.this.unit);

					if (this.stocks == null) {
						this.stocks = new ArrayList<>();
					}

					this.stocks.add(stock);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		return this;
	}

	public StockBuilder quantity(final int number) {
		this.number = number;
		return this;
	}

	public StockBuilder unit(final Unit unit) {
		this.unit = unit;
		return this;

	}

	private Stock createStock(final Stock stock, final Unit unit) throws BusinessException {

		ProductDescription productDescription = stock.getProductDescription().get();

		final Product product = this.productService.createProduct(this.getUserContext(), productDescription.getProduct().get());

		final ProductUnit productUnit = this.productUnitService.createProductUnit(this.getUserContext(),
				productDescription.getProductUnit().get());

		productDescription.setProduct(product);
		productDescription.setProductUnit(productUnit);

		productDescription = this.productDescriptionService.createProductDescription(this.getUserContext(),
				productDescription);

		stock.setUnit(unit);
		stock.setProductDescription(productDescription);
		stock.setStockStatus();

		return this.stockService.createStock(this.getUserContext(), stock);
	}
}
