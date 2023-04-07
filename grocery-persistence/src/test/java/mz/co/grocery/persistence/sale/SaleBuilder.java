/**
 *
 */
package mz.co.grocery.persistence.sale;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.item.service.ServiceDescriptionService;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.service.CashSaleServiceImpl;
import mz.co.grocery.core.sale.service.InstallmentSaleServiceImpl;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.saleable.service.ServiceItemService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.GroceryTemplate;
import mz.co.grocery.persistence.fixturefactory.SaleItemTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
@Ignore
@Service
public class SaleBuilder extends AbstractIntegServiceTest {

	@Inject
	private StockService stockService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceItemService serviceItemService;

	@Inject
	private CustomerService customerService;

	@Inject
	@Qualifier(CashSaleServiceImpl.NAME)
	private SaleService cashSaleService;

	@Inject
	@Qualifier(InstallmentSaleServiceImpl.NAME)
	private SaleService installmentSaleService;

	private Sale sale;

	public SaleBuilder sale() {
		this.sale = new Sale();
		this.sale.setSaleDate(LocalDate.now());
		return this;
	}

	public SaleBuilder withProducts(final int quantity) {
		final List<SaleItem> products = EntityFactory.gimme(SaleItem.class, quantity, SaleItemTemplate.PRODUCT);

		products.forEach(saleItem -> {

			try {
				this.productService.createProduct(this.getUserContext(),
						saleItem.getStock().getProductDescription().getProduct());
				this.productSizeService.createProductUnit(this.getUserContext(),
						saleItem.getStock().getProductDescription().getProductUnit());
				this.productDescriptionService.createProductDescription(this.getUserContext(),
						saleItem.getStock().getProductDescription());
				this.groceryService.createGrocery(this.getUserContext(), saleItem.getStock().getGrocery());
				this.stockService.createStock(this.getUserContext(), saleItem.getStock());
			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(saleItem);
		});

		return this;
	}

	public SaleBuilder withServices(final int quantity) {

		final List<SaleItem> services = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SERVICE);

		services.forEach(service -> {

			try {
				this.serviceService.createService(this.getUserContext(), service.getServiceItem().getServiceDescription().getService());
				this.serviceDescriptionService.createServiceDescription(this.getUserContext(), service.getServiceItem().getServiceDescription());
				this.groceryService.createGrocery(this.getUserContext(), service.getServiceItem().getUnit());
				this.serviceItemService.createServiceItem(this.getUserContext(), service.getServiceItem());

			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(service);
		});

		return this;
	}

	public SaleBuilder withUnit() throws BusinessException {

		final Grocery unit = this.groceryService.createGrocery(this.getUserContext(),
				EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		this.sale.setGrocery(unit);

		return this;
	}

	public SaleBuilder saleType(final SaleType saleType) {
		this.sale.setSaleType(saleType);
		return this;
	}

	public SaleBuilder withCustomer() throws BusinessException {
		final Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(this.groceryService.createGrocery(this.getUserContext(),
				EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID)));
		this.customerService.createCustomer(this.getUserContext(), customer);
		this.sale.setCustomer(customer);
		return this;
	}

	public SaleBuilder dueDate(final LocalDate dueDate) {
		this.sale.setDueDate(dueDate);
		return this;
	}

	public Sale build() throws BusinessException {

		if (SaleType.CASH.equals(this.sale.getSaleType())) {
			return this.cashSaleService.registSale(this.getUserContext(), this.sale);
		}

		return this.installmentSaleService.registSale(this.getUserContext(), this.sale);
	}
}
