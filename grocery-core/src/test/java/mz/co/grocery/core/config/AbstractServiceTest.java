/**
 *
 */
package mz.co.grocery.core.config;

import org.junit.BeforeClass;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.util.UuidFactory;

/**
 * @author Stélio Moiane
 *
 */
public abstract class AbstractServiceTest {

	private UserContext context;

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("mz.co.grocery.core.fixturefactory");
	}

	public UserContext getUserContext() {

		if (this.context != null) {
			return this.context;
		}

		this.context = new UserContext();

		this.context.setUuid(UuidFactory.generate());
		this.context.setUsername("steliomo");

		return this.context;
	}
}
