/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.item.ProductUnitType;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(ProductUnit.class).addTemplate(ProductUnitTemplate.VALID, new Rule() {
			{
				this.add("productUnitType",
						this.random(ProductUnitType.G, ProductUnitType.KG, ProductUnitType.L, ProductUnitType.ML));
				this.add("unit", this.random(BigDecimal.class, this.range(1, 100)));
			}
		});
	}

}
