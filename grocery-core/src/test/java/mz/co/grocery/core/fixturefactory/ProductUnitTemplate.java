/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.grocery.core.product.model.ProductUnitType;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(ProductUnit.class).addTemplate(VALID, new Rule() {
			{
				this.add("productUnitType",
				        this.random(ProductUnitType.G, ProductUnitType.KG, ProductUnitType.L, ProductUnitType.ML));
				this.add("unit", this.random(BigDecimal.class, this.range(1, 100)));
			}
		});
	}

}
