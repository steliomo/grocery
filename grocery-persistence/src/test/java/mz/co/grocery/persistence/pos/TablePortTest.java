/**
 *
 */
package mz.co.grocery.persistence.pos;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.sale.out.SalePort;
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

public class TablePortTest extends AbstractIntegServiceTest {

	@Inject
	private SalePort salePort;

	@Inject
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private OpenTableUseCase openTableUseCase;

	private Sale table;

	@Before
	public void setup() throws BusinessException {
		this.table = new Sale();

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(unit);
		customer.setContact(customer.getContact() + "01");
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);

		this.table.setUnit(unit);
		this.table.setCustomer(customer);

		this.table = this.openTableUseCase.openTable(this.getUserContext(), this.table);
	}

	@Test
	public void shouldFetchOpendTables() throws BusinessException {

		final List<Sale> tables = this.salePort.fetchOpenedTables(this.table.getUnit().get().getUuid());

		Assert.assertFalse(tables.isEmpty());

		tables.forEach(table -> {
			Assert.assertEquals(SaleStatus.OPENED, table.getSaleStatus());
			Assert.assertTrue(table.getCustomer().isPresent());
		});

	}

}
