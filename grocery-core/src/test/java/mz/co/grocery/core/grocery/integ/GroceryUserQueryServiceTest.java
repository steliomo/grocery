/**
 *
 */
package mz.co.grocery.core.grocery.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryUserTemplate;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.grocery.service.GroceryUserQueryService;
import mz.co.grocery.core.grocery.service.GroceryUserService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryUserQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private GroceryService groceryService;

	@Inject
	private GroceryUserService groceryUserService;

	@Inject
	private GroceryUserQueryService groceryUserQueryService;

	@Before
	public void setup() {

		final List<GroceryUser> groceryUsers = EntityFactory.gimme(GroceryUser.class, 10, GroceryUserTemplate.VALID);
		this.createGroceryUser(groceryUsers);

	}

	private void createGroceryUser(final List<GroceryUser> groceryUsers) {
		groceryUsers.forEach(groceryUser -> {
			try {
				this.groceryService.createGrocery(this.getUserContext(), groceryUser.getGrocery());
				this.groceryUserService.createGroceryUser(this.getUserContext(), groceryUser);
			}
			catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFetchdAllGroceryUsers() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 8;

		final List<GroceryUser> groceryUsers = this.groceryUserQueryService.fetchAllGroceryUsers(currentPage,
		        maxResult);

		assertFalse(groceryUsers.isEmpty());
		assertEquals(maxResult, groceryUsers.size());

		groceryUsers.forEach(groceryUser -> {
			assertNotNull(groceryUser.getGrocery());
		});
	}

	@Test
	public void shouldFetchGroceryUserByUser() throws BusinessException {

		final GroceryUser groceryUser = EntityFactory.gimme(GroceryUser.class, GroceryUserTemplate.VALID);
		groceryUser.setUser(groceryUser.getUser() + "ST");

		this.groceryService.createGrocery(this.getUserContext(), groceryUser.getGrocery());
		this.groceryUserService.createGroceryUser(this.getUserContext(), groceryUser);

		final GroceryUser groceryUserFound = this.groceryUserQueryService.fetchGroceryUserByUser(groceryUser.getUser());

		assertEquals(groceryUser.getUser(), groceryUserFound.getUser());
		assertNotNull(groceryUserFound.getGrocery());

	}
}
