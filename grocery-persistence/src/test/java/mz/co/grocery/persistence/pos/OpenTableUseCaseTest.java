/**
 *
 */
package mz.co.grocery.persistence.pos;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class OpenTableUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private OpenTableUseCase openTableUseCase;

	@Inject
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Test
	public void shouldOpenTable() throws BusinessException {
		Sale table = new Sale();

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(unit);
		customer.setContact(customer.getContact() + "0");
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);

		table.setUnit(unit);
		table.setCustomer(customer);

		table = this.openTableUseCase.openTable(this.getUserContext(), table);

		Assert.assertEquals(table.getSaleStatus(), SaleStatus.OPENED);
		Assert.assertNotNull(table.getUnit());
		Assert.assertNotNull(table.getCustomer());
	}
}
