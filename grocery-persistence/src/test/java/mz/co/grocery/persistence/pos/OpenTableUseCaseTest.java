/**
 *
 */
package mz.co.grocery.persistence.pos;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.pos.out.SaleListner;
import mz.co.grocery.core.application.pos.out.SaleNotifier;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.grocery.persistence.pos.adapter.SendSaleToDBListner;
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

	@Inject
	private SaleNotifier saleNotifier;

	@Inject
	@BeanQualifier(SendSaleToDBListner.NAME)
	private SaleListner sendToDBListner;

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

		this.saleNotifier.registListner(this.sendToDBListner);

		this.openTableUseCase.setSaleNotifier(this.saleNotifier);
		table = this.openTableUseCase.openTable(this.getUserContext(), table);

		Assert.assertEquals(table.getSaleStatus(), SaleStatus.OPENED);
		Assert.assertNotNull(table.getUnit());
		Assert.assertNotNull(table.getCustomer());
	}
}
