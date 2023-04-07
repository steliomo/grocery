/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;



import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.item.model.Service;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(Service.class).addTemplate(ServiceTemplate.VALID, new Rule() {
			{
				this.add("name", "Corte de Cabelo");
			}
		});
	}
}
