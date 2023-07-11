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
import mz.co.grocery.core.application.unit.out.UnitUserPort;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.domain.unit.UnitDetail;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GroceryUserTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class UnitUserPortTest extends AbstractIntegServiceTest {

	@Inject
	private UnitPort unitPort;

	@Inject
	private UnitUserPort unitUserPort;

	private String unitUuid;

	@Before
	public void setup() {

		final List<UnitUser> groceryUsers = EntityFactory.gimme(UnitUser.class, 10, GroceryUserTemplate.VALID);
		this.createGroceryUser(groceryUsers);

	}

	private void createGroceryUser(final List<UnitUser> unitUsers) {
		unitUsers.forEach(unitUser -> {
			try {
				final Unit unit = this.unitPort.createUnit(this.getUserContext(), unitUser.getUnit().get());
				this.unitUuid = unit.getUuid();
				unitUser.setUnit(unit);
				this.unitUserPort.createUnitUser(this.getUserContext(), unitUser);
			} catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFetchdAllGroceryUsers() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 8;

		final List<UnitUser> groceryUsers = this.unitUserPort.fetchAllUnitUsers(currentPage,
				maxResult);

		Assert.assertFalse(groceryUsers.isEmpty());
		Assert.assertEquals(maxResult, groceryUsers.size());

		groceryUsers.forEach(groceryUser -> {
			Assert.assertNotNull(groceryUser.getUnit());
		});
	}

	@Test
	public void shouldFetchGroceryUserByUser() throws BusinessException {

		final UnitUser unitUser = EntityFactory.gimme(UnitUser.class, GroceryUserTemplate.VALID);
		unitUser.setUser(unitUser.getUser() + "ST");

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), unitUser.getUnit().get());
		unitUser.setUnit(unit);

		this.unitUserPort.createUnitUser(this.getUserContext(), unitUser);

		final UnitUser groceryUserFound = this.unitUserPort.fetchUnitUserByUser(unitUser.getUser());

		Assert.assertEquals(unitUser.getUser(), groceryUserFound.getUser());
		Assert.assertNotNull(groceryUserFound.getUnit());
	}

	@Test
	public void shouldFindUnitDetails() throws BusinessException {
		final UnitDetail groceryDetail = this.unitUserPort.findUnitDetailsByUuid(this.unitUuid);

		Assert.assertEquals(groceryDetail.getUuid(), this.unitUuid);
	}
}
