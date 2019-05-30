/**
 *
 */
package mz.co.grocery.core.sale.unit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemTest extends AbstractUnitServiceTest {

	private SaleItem saleItem;

	@Before
	public void before() {
		this.saleItem = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.VALID);
	}

	@Test
	public void shouldCalculateTotalItemSale() {

		final BigDecimal totalSale = this.saleItem.getStock().getSalePrice().multiply(this.saleItem.getQuantity())
		        .subtract(this.saleItem.getDiscount());

		assertEquals(totalSale, this.saleItem.getTotalSaleItem());
	}

	@Test
	public void shouldCalculateProfitItemSale() {

		final BigDecimal totalProfit = this.saleItem.getStock().getSalePrice()
		        .subtract(this.saleItem.getStock().getPurchasePrice()).multiply(this.saleItem.getQuantity())
		        .subtract(this.saleItem.getDiscount());

		assertEquals(totalProfit, this.saleItem.getTotalProfit());
	}
}
