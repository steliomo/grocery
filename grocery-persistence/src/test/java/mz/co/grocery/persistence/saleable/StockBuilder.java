/**
 *
 */
package mz.co.grocery.persistence.saleable;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.persistence.config.AbstractServiceTest;
import mz.co.grocery.persistence.fixturefactory.StockTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
@Service(StockBuilder.NAME)
public class StockBuilder extends AbstractServiceTest {

	public static final String NAME = "mz.co.grocery.core.saleable.builder.StockBuilder";

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private StockService stockService;

	private List<Stock> stocks;

	private int number;

	private Grocery unit;

	public List<Stock> build() {

		return this.stocks;
	}

	public StockBuilder inAnalysis() {
		this.stocks = EntityFactory.gimme(Stock.class, this.number, StockTemplate.IN_ANALYSIS, result -> {
			if (result instanceof Stock) {
				final Stock stock = (Stock) result;
				StockBuilder.this.createStock(stock, StockBuilder.this.unit);
			}
		});

		return this;
	}

	public StockBuilder valid() {
		this.stocks = EntityFactory.gimme(Stock.class, this.number, StockTemplate.VALID, result -> {
			if (result instanceof Stock) {
				final Stock stock = (Stock) result;
				StockBuilder.this.createStock(stock, StockBuilder.this.unit);
			}
		});

		return this;
	}

	public StockBuilder quantity(final int number) {
		this.number = number;
		return this;
	}

	public StockBuilder unit(final Grocery unit) {
		this.unit = unit;
		return this;

	}

	private void createStock(final Stock stock, final Grocery unit) {
		try {
			this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());
			this.productUnitService.createProductUnit(this.getUserContext(),
					stock.getProductDescription().getProductUnit());
			this.productDescriptionService.createProductDescription(this.getUserContext(),
					stock.getProductDescription());
			stock.setGrocery(unit);
			this.stockService.createStock(this.getUserContext(), stock);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

}
