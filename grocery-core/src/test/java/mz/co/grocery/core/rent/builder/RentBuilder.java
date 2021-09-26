/**
 *
 */
package mz.co.grocery.core.rent.builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.config.AbstractServiceTest;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.RentTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.item.service.ServiceDescriptionService;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.service.ServiceItemService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
@Service(RentBuilder.NAME)
public class RentBuilder extends AbstractServiceTest {

	public static final String NAME = "mz.co.grocery.core.rent.builder.RentBuilder";

	@Inject
	private GroceryService groceryService;

	@Inject
	private CustomerService customerService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private StockService stockService;

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceItemService serviceItemService;

	public Rent build() throws BusinessException {

		final Rent rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID, result -> {

			if (result instanceof Grocery || result instanceof Customer) {
				return;
			}

			final Rent Rent = (Rent) result;
			final List<RentItem> products = EntityFactory.gimme(RentItem.class, 5, RentItemTemplate.PRODUCT);
			final List<RentItem> services = EntityFactory.gimme(RentItem.class, 5, RentItemTemplate.SERVICE);

			final List<RentItem> rentItems = Stream.concat(products.stream(), services.stream()).collect(Collectors.toList());

			rentItems.forEach(rentItem -> {
				Rent.addRentItem(rentItem);

				if (ItemType.PRODUCT.equals(rentItem.getType())) {
					this.createProduct(rentItem);
					return;
				}

				this.createService(rentItem);
			});
		});

		this.groceryService.createGrocery(this.getUserContext(), rent.getUnit());
		rent.getCustomer().setUnit(rent.getUnit());
		this.customerService.createCustomer(this.getUserContext(), rent.getCustomer());

		return rent;
	}

	private void createProduct(final RentItem rentItem) {
		final Stock stock = (Stock) rentItem.getItem();

		try {
			this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());
			this.productUnitService.createProductUnit(this.getUserContext(), stock.getProductDescription().getProductUnit());
			this.productDescriptionService.createProductDescription(this.getUserContext(), stock.getProductDescription());
			this.groceryService.createGrocery(this.getUserContext(), stock.getGrocery());
			this.stockService.createStock(this.getUserContext(), stock);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	private void createService(final RentItem rentItem) {
		final ServiceItem serviceItem = (ServiceItem) rentItem.getItem();

		try {
			this.groceryService.createGrocery(this.getUserContext(), serviceItem.getUnit());
			this.serviceService.createService(this.getUserContext(), serviceItem.getServiceDescription().getService());
			this.serviceDescriptionService.createServiceDescription(this.getUserContext(), serviceItem.getServiceDescription());
			this.serviceItemService.createServiceItem(this.getUserContext(), serviceItem);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}
}
