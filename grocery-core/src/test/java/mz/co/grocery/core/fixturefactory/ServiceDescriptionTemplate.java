/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.product.model.Service;
import mz.co.grocery.core.product.model.ServiceDescription;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(ServiceDescription.class).addTemplate(ServiceDescriptionTemplate.VALID, new Rule() {
			{
				this.add("service", this.one(Service.class, ServiceTemplate.VALID));
				this.add("description", "Normal");
			}
		});
	}
}
