/**
 *
 */
package mz.co.grocery.persistence.grocery;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class UnitPortTest extends AbstractIntegServiceTest {

	@Inject
	private UnitPort unitPort;

	private String unitName;

	@Before
	public void setup() {
		EntityFactory.gimme(Unit.class, 10, UnitTemplate.VALID, result -> {
			try {

				final Unit unit = (Unit) result;
				this.unitPort.createUnit(this.getUserContext(), unit);
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

		final List<Unit> groceries = this.unitPort.findAllGroceries(currentPage, maxResult);
		Assert.assertFalse(groceries.isEmpty());
		Assert.assertEquals(maxResult, groceries.size());
	}

	@Test
	public void shouldFindUnitsByName() throws BusinessException {

		final List<Unit> units = this.unitPort.findUnitsByName(this.unitName);
		Assert.assertFalse(units.isEmpty());

		units.forEach(unit -> {
			Assert.assertTrue(unit.getName().contains(this.unitName));
		});
	}
}
