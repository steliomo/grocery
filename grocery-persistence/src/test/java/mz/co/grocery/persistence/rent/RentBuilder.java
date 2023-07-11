/**
 *
 */
package mz.co.grocery.persistence.rent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.item.out.ServiceDescriptionPort;
import mz.co.grocery.core.application.item.out.ServicePort;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.item.ItemType;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractServiceTest;
import mz.co.grocery.persistence.fixturefactory.RentItemTemplate;
import mz.co.grocery.persistence.fixturefactory.RentTemplate;
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
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private StockPort stockPort;

	@Inject
	private ServicePort servicePort;

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Inject
	private ServiceItemPort serviceItemPort;

	public Rent build() throws BusinessException {

		final Rent rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID, result -> {

			if (result instanceof Unit || result instanceof Customer) {
				return;
			}

			final Rent rentResult = (Rent) result;
			final List<RentItem> products = EntityFactory.gimme(RentItem.class, 5, RentItemTemplate.PRODUCT);
			final List<RentItem> services = EntityFactory.gimme(RentItem.class, 5, RentItemTemplate.SERVICE);

			final List<RentItem> rentItems = Stream.concat(products.stream(), services.stream()).collect(Collectors.toList());

			rentItems.forEach(rentItem -> {
				if (ItemType.PRODUCT.equals(rentItem.getType())) {
					this.createProduct(rentItem);
					rentResult.addRentItem(rentItem);
					return;
				}

				this.createService(rentItem);

				rentResult.addRentItem(rentItem);
			});
		});

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), rent.getUnit().get());

		Customer customer = rent.getCustomer().get();
		customer.setUnit(unit);

		customer = this.customerPort.createCustomer(this.getUserContext(), rent.getCustomer().get());

		rent.setUnit(unit);
		rent.setCustomer(customer);

		return rent;
	}

	private void createProduct(final RentItem rentItem) {
		Stock stock = (Stock) rentItem.getItem().get();

		try {

			ProductDescription productDescription = stock.getProductDescription().get();

			final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());
			final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(),
					stock.getProductDescription().get().getProductUnit().get());

			productDescription.setProduct(product);
			productDescription.setProductUnit(productUnit);

			productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(), productDescription);

			final Unit unit = this.unitPort.createUnit(this.getUserContext(), stock.getUnit().get());

			stock.setUnit(unit);
			stock.setProductDescription(productDescription);
			stock.setStockStatus();

			stock = this.stockPort.createStock(this.getUserContext(), stock);
			rentItem.setItem(stock);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	private void createService(final RentItem rentItem) {
		ServiceItem serviceItem = (ServiceItem) rentItem.getItem().get();

		try {
			final Unit unit = this.unitPort.createUnit(this.getUserContext(), serviceItem.getUnit().get());

			ServiceDescription serviceDescription = serviceItem.getServiceDescription().get();
			serviceDescription.setService(this.servicePort.createService(this.getUserContext(), serviceDescription.getService().get()));
			serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(), serviceDescription);

			serviceItem.setUnit(unit);
			serviceItem.setServiceDescription(serviceDescription);

			serviceItem = this.serviceItemPort.createServiceItem(this.getUserContext(), serviceItem);
			rentItem.setItem(serviceItem);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}
}
