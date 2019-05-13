/**
 *
 */
package mz.co.grocery.core.stock.unit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.stock.dao.StockDAO;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.service.StockService;
import mz.co.grocery.core.stock.service.StockServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public class StockServiceTest extends AbstractUnitServiceTest {

	@Mock
	private StockDAO stockDAO;

	@InjectMocks
	private final StockService stockService = new StockServiceImpl();

	private Stock stock;

	@Before
	public void before() {
		this.stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
	}

	@Test
	public void shouldRemoveStock() throws BusinessException {
		this.stockService.removeStock(this.getUserContext(), this.stock);
		assertEquals(EntityStatus.INACTIVE, this.stock.getEntityStatus());
	}

	@Test
	public void shouldAddStockQuantity() throws BusinessException {
		final BigDecimal quantity = new BigDecimal(10);
		final BigDecimal oldQuantity = this.stock.getQuantity();

		this.stockService.addStockQuantity(this.getUserContext(), this.stock, quantity);

		assertEquals(oldQuantity.add(quantity), this.stock.getQuantity());
	}
}
