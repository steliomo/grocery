/**
 *
 */
package mz.co.grocery.core.customer.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.service.CustomerQueryService;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.rent.builder.RentBuilder;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class CustomerQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private GroceryService groceryService;

	@Inject
	private CustomerService customerService;

	@Inject
	private CustomerQueryService customerQueryService;

	@Inject
	private RentService rentService;

	@Inject
	private RentBuilder rentBuilder;

	private Grocery unit;

	@Before
	public void setup() throws BusinessException {
		this.unit = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);

		this.groceryService.createGrocery(this.getUserContext(), this.unit);

		final List<Customer> customers = EntityFactory.gimme(Customer.class, 10, CustomerTemplate.VALID, processor -> {
			if (processor instanceof Customer) {
				final Customer customer = (Customer) processor;
				customer.setUnit(this.unit);
			}
		});

		customers.stream().forEach(customer -> {
			this.registCustomer(customer);
		});
	}

	private void registCustomer(final Customer customer) {
		try {
			this.customerService.createCustomer(this.getUserContext(), customer);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldCountCustomerByUnit() throws BusinessException {
		final Long totalCustomer = this.customerQueryService.countCustomersByUnit(this.unit.getUuid());

		Assert.assertEquals(10, totalCustomer, 0);
	}

	@Test
	public void shouldFindCustomersByUnit() throws BusinessException {
		final List<Customer> customers = this.customerQueryService.findCustomersByUnit(this.unit.getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());
		Assert.assertEquals(10, customers.size());
	}

	@Test
	public void shouldFindCustomersWithPendingPaymentsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final List<Customer> customers = this.customerQueryService.findCustomersWithPendingPeymentsByUnit(rent.getUnit().getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());
		Assert.assertEquals(1, customers.size());
	}

	@Test
	public void shouldCountCustomersWithPendingPaymentsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final Long customers = this.customerQueryService.countCustomersWithPendingPeymentsByUnit(rent.getUnit().getUuid());

		Assert.assertTrue(customers > 0);
	}

	@Test
	public void shouldfindCustomersWithPendindDevolutionsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final List<Customer> customers = this.customerQueryService.findCustomersWithPendingDevolutionsByUnit(rent.getUnit().getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());
		customers.forEach(customer -> {
			Assert.assertEquals(rent.getCustomer().getUuid(), customer.getUuid());
		});
	}

	@Test
	public void shouldCountCustomersWithPendindDevolutionsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final Long customers = this.customerQueryService.countCustomersWithPendingDevolutionsByUnit(rent.getUnit().getUuid());

		Assert.assertTrue(customers > 0);
	}
}
