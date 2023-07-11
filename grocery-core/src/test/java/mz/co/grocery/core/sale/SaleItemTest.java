/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemTest extends AbstractUnitServiceTest {

	private SaleItem saleItem;

	@Before
	public void before() {
		this.saleItem = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.PRODUCT);
	}

	@Test
	public void shouldCalculateTotalItemSale() {

		final BigDecimal totalSale = this.saleItem.getSaleItemValue().subtract(this.saleItem.getDiscount());

		Assert.assertEquals(totalSale, this.saleItem.getTotalSaleItem());
	}

	@Test
	public void shouldCalculateProfitItemSale() {

		final BigDecimal totalProfit = this.saleItem.getStock().get().getSalePrice()
				.subtract(this.saleItem.getStock().get().getPurchasePrice()).multiply(this.saleItem.getQuantity())
				.subtract(this.saleItem.getDiscount());

		Assert.assertEquals(totalProfit, this.saleItem.getTotalBilling());
	}
}
