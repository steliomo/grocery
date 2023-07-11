/**
 *
 */
package mz.co.grocery.persistence.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.application.contract.in.CelebrateContractUseCase;
import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.DeliveryGuideIssuer;
import mz.co.grocery.core.application.guide.service.TransportGuideIssuer;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ContractTemplate;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.grocery.persistence.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.grocery.persistence.rent.RentBuilder;
import mz.co.grocery.persistence.sale.SaleBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class CustomerPortTest extends AbstractIntegServiceTest {

	@Inject
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private RentPort rentPort;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	private CelebrateContractUseCase contractService;

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private IssueGuideUseCase guideService;

	@Inject
	@BeanQualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	@Qualifier(DeliveryGuideIssuer.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Inject
	private RentPaymentPort rentPaymentPort;

	@Inject
	private RentItemPort rentItemPort;

	@Inject
	private SaleItemPort saleItemPort;

	private Unit unit;

	@Before
	public void setup() throws BusinessException {
		this.unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);

		this.unit = this.unitPort.createUnit(this.getUserContext(), this.unit);

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
			this.customerPort.createCustomer(this.getUserContext(), customer);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldCountCustomerByUnit() throws BusinessException {
		final Long totalCustomer = this.customerPort.countCustomersByUnit(this.unit.getUuid());

		Assert.assertEquals(10, totalCustomer, 0);
	}

	@Test
	public void shouldFindCustomersByUnit() throws BusinessException {
		final List<Customer> customers = this.customerPort.findCustomersByUnit(this.unit.getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());
		Assert.assertEquals(10, customers.size());
	}

	@Test
	public void shouldFindCustomersWithRentPendingPaymentsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentPort.createRent(this.getUserContext(), rent);

		final List<Customer> customers = this.customerPort.findCustomersWithRentPendingPeymentsByUnit(rent.getUnit().get().getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());
		Assert.assertEquals(1, customers.size());
	}

	@Test
	public void shouldCountCustomersWithPendingPaymentsByUnit() throws BusinessException {

		final Rent rent = this.rentBuilder.build();
		this.rentPort.createRent(this.getUserContext(), rent);

		final Long customers = this.customerPort.countCustomersWithPendingPeymentsByUnit(rent.getUnit().get().getUuid());

		Assert.assertTrue(customers > 0);
	}

	@Test
	public void shouldFindCustomersWithPendingOrIncompleteRentItemsToReturnByUnit() throws BusinessException {

		Rent rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = rent.getRentItems().get();
		rent = this.rentPort.createRent(this.getUserContext(), rent);

		final Guide guide = new Guide();
		guide.setRent(rent);
		guide.setType(GuideType.TRANSPORT);

		for (RentItem rentItem : rentItems) {
			rentItem.calculatePlannedTotal();
			rentItem.setRent(rent);
			rentItem.setStockable();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		}

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Customer> customers = this.customerPort
				.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(rent.getUnit().get().getUuid(), 0, 10);

		Assert.assertFalse(customers.isEmpty());

		for (final Customer customer : customers) {
			Assert.assertEquals(rent.getCustomer().get().getUuid(), customer.getUuid());
		}
	}

	@Test
	public void shouldCountCustomersWithPendingOrIncompleteRentItemsToReturnByUnit() throws BusinessException {

		Rent rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = rent.getRentItems().get();
		rent = this.rentPort.createRent(this.getUserContext(), rent);

		final Guide guide = new Guide();
		guide.setRent(rent);
		guide.setType(GuideType.TRANSPORT);

		for (RentItem rentItem : rentItems) {
			rentItem.calculatePlannedTotal();
			rentItem.setRent(rent);
			rentItem.setStockable();
			rentItem.setReturnStatus();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		}

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final Long customers = this.customerPort.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(rent.getUnit().get().getUuid());

		Assert.assertTrue(customers > 0);
	}

	@Test
	public void shouldFindCustomersWithPendingContractPaymentByUnit() throws BusinessException {

		Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID, processor -> {
			if (processor instanceof Contract) {
				final Contract c = (Contract) processor;
				try {
					final Unit unit = this.unitPort.createUnit(this.getUserContext(), c.getUnit().get());

					Customer customer = c.getCustomer().get();
					customer.setUnit(unit);

					customer = this.customerPort.createCustomer(this.getUserContext(), customer);

					c.setUnit(unit);
					c.setCustomer(customer);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		contract = this.contractService.celebrateContract(this.getUserContext(), contract);

		final List<Customer> customers = this.customerPort.findCustomersWithContractPendingPaymentByUnit(contract.getUnit().get().getUuid(), 0, 10,
				LocalDate.now());

		Assert.assertFalse(customers.isEmpty());

		for (final Customer customer : customers) {
			Assert.assertEquals(contract.getCustomer().get().getUuid(), customer.getUuid());
		}
	}

	@Test
	public void shouldCountCustomersWithPendingContractPaymentByUnit() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID, processor -> {
			if (processor instanceof Contract) {
				final Contract c = (Contract) processor;
				try {
					final Unit unit = this.unitPort.createUnit(this.getUserContext(), c.getUnit().get());

					Customer customer = c.getCustomer().get();
					customer.setUnit(unit);

					customer = this.customerPort.createCustomer(this.getUserContext(), customer);

					c.setUnit(unit);
					c.setCustomer(customer);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		this.contractService.celebrateContract(this.getUserContext(), contract);

		final Long customers = this.customerPort.countCustomersWithContractPendingPaymentByUnit(contract.getUnit().get().getUuid(),
				LocalDate.now());

		Assert.assertTrue(customers > 0);
	}

	@Test
	public void shouldFindCustomersSaleWithPendindOrIncompletePaymentByUnit() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withProducts(5).withServices(5).withUnit().withCustomer().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Customer> customers = this.customerPort.findCustomersSaleWithPendindOrIncompletePaymentByUnit(sale.getUnit().get().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithPendingOrInCompleteRentItemsToLoad() throws BusinessException {
		Rent rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = rent.getRentItems().get();
		rent = this.rentPort.createRent(this.getUserContext(), rent);

		for (final RentItem rentItem : rentItems) {
			rentItem.calculatePlannedTotal();
			rentItem.setRent(rent);
			rentItem.setStockable();
			rentItem.setLoadStatus();

			this.rentItemPort.createRentItem(this.getUserContext(), rentItem);
		}

		final List<Customer> customers = this.customerPort
				.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(rent.getUnit().get().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithIssuedGuidesByTypeAndUnit() throws BusinessException {

		Rent rent = this.rentBuilder.build();
		final Set<RentItem> rentItems = rent.getRentItems().get();
		rent = this.rentPort.createRent(this.getUserContext(), rent);

		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.NO_ITEMS_TRANSPORT);
		guide.setRent(rent);

		for (RentItem rentItem : rentItems) {
			rentItem.calculatePlannedTotal();
			rentItem.setRent(rent);
			rentItem.setStockable();

			rentItem = this.rentItemPort.createRentItem(this.getUserContext(), rentItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(new BigDecimal(2));
			guideItem.setRentItem(rentItem);
			guide.addGuideItem(guideItem);
		}

		this.guideService.setGuideIssuer(this.transportGuideIssuer);
		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Customer> customers = this.customerPort.findCustomersWithIssuedGuidesByTypeAndUnit(GuideType.TRANSPORT,
				rent.getUnit().get().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithPaymentsByUnit() throws BusinessException {
		Rent rent = this.rentBuilder.build();
		rent = this.rentPort.createRent(this.getUserContext(), rent);

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID);
		rentPayment.setRent(rent);

		this.rentPaymentPort.createRentPayment(this.getUserContext(), rentPayment);

		final List<Customer> customers = this.customerPort.findCustomersWithPaymentsByUnit(rent.getUnit().get().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}

	@Test
	public void shouldFindCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit() throws BusinessException {

		final Sale sale = this.saleBuilder.sale().withProducts(5).withServices(5).withUnit().withCustomer().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now().plusDays(30)).build();

		final List<Customer> customers = this.customerPort
				.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(sale.getUnit().get().getUuid());

		Assert.assertFalse(customers.isEmpty());
		Assert.assertEquals(sale.getCustomer().get().getName(), customers.get(0).getName());
	}

	@Test
	public void shouldFindCustomersWithDeliveredGuidesByUnit() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now())
				.build();

		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);

		guide.setSale(sale);

		for (SaleItem saleItem : sale.getItems().get()) {
			saleItem = this.saleItemPort.createSaleItem(this.getUserContext(), saleItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(saleItem.getQuantity());
			guideItem.setSaleItem(saleItem);
			guide.addGuideItem(guideItem);
		}

		this.guideService.issueGuide(this.getUserContext(), guide);

		final List<Customer> customers = this.customerPort.findCustomersWithDeliveredGuidesByUnit(sale.getUnit().get().getUuid());

		Assert.assertFalse(customers.isEmpty());
	}
}
