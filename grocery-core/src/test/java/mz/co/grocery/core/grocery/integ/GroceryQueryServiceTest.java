/**
 *
 */
package mz.co.grocery.core.grocery.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryQueryService;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private GroceryService groceryService;

	@Inject
	private GroceryQueryService groceryQueryService;

	@Before
	public void setup() {
		final List<Grocery> groceries = EntityFactory.gimme(Grocery.class, 10, GroceryTemplate.VALID);
		groceries.forEach(grocery -> {
			this.createGrocery(grocery);
		});
	}

	private void createGrocery(final Grocery grocery) {
		try {
			this.groceryService.createGrocery(this.getUserContext(), grocery);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFindAllGroceries() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 5;

		final List<Grocery> groceries = this.groceryQueryService.findAllGroceries(currentPage, maxResult);
		assertFalse(groceries.isEmpty());
		assertEquals(maxResult, groceries.size());
	}

}
