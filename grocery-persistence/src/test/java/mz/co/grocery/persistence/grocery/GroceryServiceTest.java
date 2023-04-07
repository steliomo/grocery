/**
 *
 */
package mz.co.grocery.persistence.grocery;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GroceryTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private GroceryService groceryService;

	private Grocery grocery;

	@Before
	public void setup() {
		this.grocery = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);
	}

	@Test
	public void shouldCreateGrocery() throws BusinessException {
		this.groceryService.createGrocery(this.getUserContext(), this.grocery);
		TestUtil.assertCreation(this.grocery);
	}
}
