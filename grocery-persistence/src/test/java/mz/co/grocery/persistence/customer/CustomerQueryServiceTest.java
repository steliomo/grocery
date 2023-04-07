/**
 *
 */
package mz.co.grocery.persistence.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.service.ContractService;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.customer.service.CustomerQueryService;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.guide.service.DeliveryGuideIssuerImpl;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.guide.service.TransportGuideIssuerImpl;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ContractTemplate;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.GroceryTemplate;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.grocery.persistence.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.persistence.rent.RentBuilder;
import mz.co.grocery.persistence.sale.SaleBuilder;
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

	@Inject
	private GroceryService unitService;

	@Inject
	private ContractService contractService;

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private GuideService guideService;

	@Inject
	@Qualifier(TransportGuideIssuerImpl.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	@Qualifier(DeliveryGuideIssuerImpl.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Inject
	private RentPaymentService rentPaymentService;

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
	public void shouldFindCustomersWithRentPendingPaymentsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final List<Customer> customers = this.customerQueryService.findCustomersWithRentPendingPeymentsByUnit(rent.getUnit().getUuid(), 0, 10);

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
	public void shouldFindCustomersWithPendingOrIncompleteRentItemsToReturnByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final Guide guide = new Guide();
		guide.setRent(rent);
		guide.setType(GuideType.TRANSPORT);

		rent.getRentItems().forEach(rentItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		});
		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Customer> customers = this.customerQueryService
				.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(rent.getUnit().getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());
		customers.forEach(customer -> {
			Assert.assertEquals(rent.getCustomer().getUuid(), customer.getUuid());
		});
	}

	@Test
	public void shouldCountCustomersWithPendingOrIncompleteRentItemsToReturnByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final Guide guide = new Guide();
		guide.setRent(rent);
		guide.setType(GuideType.TRANSPORT);

		rent.getRentItems().forEach(rentItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		});
		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final Long customers = this.customerQueryService.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(rent.getUnit().getUuid());

		Assert.assertTrue(customers > 0);
	}

	@Test
	public void shouldFindCustomersWithPendingContractPaymentByUnit() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID, processor -> {
			if (processor instanceof Contract) {
				final Contract c = (Contract) processor;
				try {
					this.unitService.createGrocery(this.getUserContext(), c.getUnit());
					c.getCustomer().setUnit(c.getUnit());
					this.customerService.createCustomer(this.getUserContext(), c.getCustomer());
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		this.contractService.celebrateContract(this.getUserContext(), contract);

		final List<Customer> customers = this.customerQueryService.findCustomersWithContractPendingPaymentByUnit(contract.getUnit().getUuid(), 0, 10,
				LocalDate.now());

		Assert.assertFalse(customers.isEmpty());
		customers.forEach(customer -> {
			Assert.assertEquals(contract.getCustomer().getUuid(), customer.getUuid());
		});
	}

	@Test
	public void shouldCountCustomersWithPendingContractPaymentByUnit() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID, processor -> {
			if (processor instanceof Contract) {
				final Contract c = (Contract) processor;
				try {
					this.unitService.createGrocery(this.getUserContext(), c.getUnit());
					c.getCustomer().setUnit(c.getUnit());
					this.customerService.createCustomer(this.getUserContext(), c.getCustomer());
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		this.contractService.celebrateContract(this.getUserContext(), contract);

		final Long customers = this.customerQueryService.countCustomersWithContractPendingPaymentByUnit(contract.getUnit().getUuid(),
				LocalDate.now());

		Assert.assertTrue(customers > 0);
	}

	@Test
	public void shouldFindCustomersSaleWithPendindOrIncompletePaymentByUnit() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withProducts(5).withServices(5).withUnit().withCustomer().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Customer> customers = this.customerQueryService.findCustomersSaleWithPendindOrIncompletePaymentByUnit(sale.getGrocery().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithPendingOrInCompleteRentItemsToLoad() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final List<Customer> customers = this.customerQueryService
				.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(rent.getUnit().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithIssuedGuidesByTypeAndUnit() throws BusinessException {
		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_TRANSPORT);
		guide.setRent(rent);

		rent.getRentItems().forEach(rentItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setRentItem(rentItem);
			guideItem.setQuantity(new BigDecimal(2));

			guide.addGuideItem(guideItem);
		});

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Customer> customers = this.customerQueryService.findCustomersWithIssuedGuidesByTypeAndUnit(GuideType.TRANSPORT,
				rent.getUnit().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithPaymentsByUnit() throws BusinessException {
		final Rent rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), rent);

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID, processor -> {
			if (processor instanceof RentPayment) {
				final RentPayment payment = (RentPayment) processor;
				payment.setRent(rent);
			}
		});
		this.rentPaymentService.makeRentPayment(this.getUserContext(), rentPayment);

		final List<Customer> customers = this.customerQueryService.findCustomersWithPaymentsByUnit(rent.getUnit().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withProducts(5).withServices(5).withUnit().withCustomer().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Customer> customers = this.customerQueryService
				.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(sale.getGrocery().getUuid());

		Assert.assertFalse(customers.isEmpty());
		Assert.assertEquals(sale.getCustomer().getName(), customers.get(0).getName());
	}

	@Test
	public void shouldFindCustomersWithDeliveredGuidesByUnit() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now())
				.build();
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);

		guide.setSale(sale);

		sale.getItems().forEach(saleItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(saleItem.getQuantity());
			guideItem.setSaleItem(saleItem);
			guide.addGuideItem(guideItem);
		});

		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Customer> customers = this.customerQueryService.findCustomersWithDeliveredGuidesByUnit(sale.getGrocery().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}
}
