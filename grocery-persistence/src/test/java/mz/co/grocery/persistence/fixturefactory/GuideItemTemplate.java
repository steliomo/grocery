/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.sale.model.SaleItem;

/**
 * @author Stélio Moiane
 *
 */
public class GuideItemTemplate implements TemplateLoader {

	public static final String RENT_PRODUCTS = "RENT_PRODUCTS";
	public static final String SALE_PRODUCTS = "SALE_PRODUCTS";

	@Override
	public void load() {
		Fixture.of(GuideItem.class).addTemplate(GuideItemTemplate.RENT_PRODUCTS, new Rule() {
			{
				this.add("rentItem", this.one(RentItem.class, RentItemTemplate.PRODUCT));
				this.add("quantity", this.random(BigDecimal.class, this.range(1, 5)));
			}
		});

		Fixture.of(GuideItem.class).addTemplate(GuideItemTemplate.SALE_PRODUCTS, new Rule() {
			{
				this.add("saleItem", this.one(SaleItem.class, SaleItemTemplate.PRODUCT));
				this.add("quantity", this.random(BigDecimal.class, this.range(1, 5)));
			}
		});
	}
}
