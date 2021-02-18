/**
 *
 */
package mz.co.grocery.core.grocery.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
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

	private String unitName;

	@Before
	public void setup() {
		EntityFactory.gimme(Grocery.class, 10, GroceryTemplate.VALID, result -> {
			try {

				final Grocery unit = (Grocery) result;
				this.groceryService.createGrocery(this.getUserContext(), unit);
				this.unitName = unit.getName();
			} catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFindAllGroceries() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 5;

		final List<Grocery> groceries = this.groceryQueryService.findAllGroceries(currentPage, maxResult);
		Assert.assertFalse(groceries.isEmpty());
		Assert.assertEquals(maxResult, groceries.size());
	}

	@Test
	public void shouldFindUnitsByName() throws BusinessException {

		final List<Grocery> units = this.groceryQueryService.findUnitsByName(this.unitName);
		Assert.assertFalse(units.isEmpty());

		units.forEach(unit -> {
			Assert.assertTrue(unit.getName().contains(this.unitName));
		});
	}
}
