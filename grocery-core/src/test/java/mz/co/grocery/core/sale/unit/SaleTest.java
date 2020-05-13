/**
 *
 */
package mz.co.grocery.core.sale.unit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SaleTest extends AbstractUnitServiceTest {

	private Sale sale;

	@Before
	public void before() throws BusinessException {
		this.sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);
	}

	@Test
	public void shouldCalculateTotalSale() {

		final BigDecimal totalSale = this.sale.getItems().stream().map(SaleItem::getTotalSaleItem)
		        .reduce(BigDecimal.ZERO, BigDecimal::add);

		this.sale.calculateTotal();

		assertEquals(totalSale, this.sale.getTotal());
	}

	@Test
	public void shouldCalculateTotalProfitSale() {

		final BigDecimal totalProfit = this.sale.getItems().stream().map(SaleItem::getTotalBilling)
		        .reduce(BigDecimal.ZERO, BigDecimal::add);

		this.sale.calculateBilling();

		assertEquals(totalProfit, this.sale.getBilling());
	}
}
