/**
 *
 */
package mz.co.grocery.core.item;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.item.service.RegistStockProductService;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RegistProductStockUseCaseTest extends AbstractUnitServiceTest {

	@InjectMocks
	private RegistStockProductService addStockProductUseCase;

	@Mock
	private StockPort stockPort;

	@Test
	public void shoulRegistStockProduct() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);

		Mockito.when(this.stockPort.fetchStockByProductAndUnit(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.empty());

		final Stock returnedStock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		returnedStock.setId(1L);

		Mockito.when(this.stockPort.createStock(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(returnedStock);

		final Stock createdStock = this.addStockProductUseCase.regist(this.getUserContext(), stock);

		Assert.assertNotNull(createdStock.getId());
	}

	@Test(expected = BusinessException.class)
	public void shoulRegistDuplicatedStockProduct() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);

		Mockito.when(this.stockPort.fetchStockByProductAndUnit(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(stock));

		this.addStockProductUseCase.regist(this.getUserContext(), stock);
	}
}
