/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.product.model.ProductSize;
import mz.co.grocery.core.product.model.ProductSizeType;

/**
 * @author Stélio Moiane
 *
 */
public class ProductSizeTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(ProductSize.class).addTemplate(VALID, new Rule() {
			{
				this.add("productSizeType", this.random(ProductSizeType.GRAM, ProductSizeType.KILOGRAM,
				        ProductSizeType.LITER, ProductSizeType.MILITRE));
				this.add("size", this.random(BigDecimal.class, this.range(1, 100)));
			}
		});
	}

}
