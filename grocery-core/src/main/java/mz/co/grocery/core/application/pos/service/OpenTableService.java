/**
 *
 */
package mz.co.grocery.core.application.pos.service;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.pos.out.SaleNotifier;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class OpenTableService extends AbstractService implements OpenTableUseCase {

	private Clock clock;

	private CustomerPort customerPort;

	private SaleNotifier saleNotifier;

	public OpenTableService(final Clock clock, final CustomerPort customerPort) {
		this.clock = clock;
		this.customerPort = customerPort;
	}

	@Override
	public Sale openTable(final UserContext context, Sale table) throws BusinessException {

		if (!table.getUnit().isPresent()) {
			throw new BusinessException("sale.must.have.unit");
		}

		if (!table.getCustomer().isPresent()) {
			throw new BusinessException("sale.owner.must.be.specified");
		}

		final Optional<Customer> customer = this.customerPort.findCustomerByContact(table.getCustomer().get().getContact());

		if (customer.isPresent()) {
			table.setCustomer(customer.get());
		} else {

			final Customer newCustomer = table.getCustomer().get();
			newCustomer.setUnit(table.getUnit().get());
			newCustomer.setAddress("NA");

			final Customer createdCustomer = this.customerPort.createCustomer(context, newCustomer);

			table.setCustomer(createdCustomer);
		}

		table.setSaleDate(this.clock.todayDate());
		table.setTotal(BigDecimal.ZERO);
		table.setBilling(BigDecimal.ZERO);
		table.setTotalPaid(BigDecimal.ZERO);
		table.setDeliveryStatus(DeliveryStatus.NA);
		table.setSaleStatus(SaleStatus.OPENED);
		table.setSaleType(SaleType.INSTALLMENT);

		table = this.saleNotifier.notify(context, table);

		return table;
	}

	@Override
	public void setSaleNotifier(final SaleNotifier saleNotifier) {
		this.saleNotifier = saleNotifier;
	}
}
