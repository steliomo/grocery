/**
 *
 */
package mz.co.grocery.persistence.grocery;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.grocery.service.GroceryUserService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GroceryUserTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryUserServiceTest extends AbstractIntegServiceTest {

	@Inject
	private GroceryService groceryService;

	@Inject
	private GroceryUserService groceryUserService;

	private GroceryUser groceryUser;

	@Before
	public void setup() throws BusinessException {
		this.groceryUser = EntityFactory.gimme(GroceryUser.class, GroceryUserTemplate.VALID);
		this.groceryService.createGrocery(this.getUserContext(), this.groceryUser.getGrocery());
	}

	@Test
	public void shouldCreateGroceryUser() throws BusinessException {
		this.groceryUserService.createGroceryUser(this.getUserContext(), this.groceryUser);
		TestUtil.assertCreation(this.groceryUser);
	}
}
