/**
 *
 */
package mz.co.grocery.persistence.sale;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.item.out.ServiceDescriptionPort;
import mz.co.grocery.core.application.item.out.ServicePort;
import mz.co.grocery.core.application.sale.in.SaleUseCase;
import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.sale.service.CashSaleService;
import mz.co.grocery.core.application.sale.service.InstallmentSaleService;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.SaleItemTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
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
	private StockPort stockPort;

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private UnitPort unitPort;

	@Inject
	private ServicePort servicePort;

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Inject
	private ServiceItemPort serviceItemPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	@Qualifier(CashSaleService.NAME)
	private SaleUseCase cashSaleUseCase;

	@Inject
	@Qualifier(InstallmentSaleService.NAME)
	private SaleUseCase installmentSaleUseCase;

	private Sale sale;

	private Set<SaleItem> items;

	public SaleBuilder sale() {
		this.sale = new Sale();
		this.sale.setSaleDate(LocalDate.now());
		return this;
	}

	public SaleBuilder withProducts(final int quantity) {
		final List<SaleItem> products = EntityFactory.gimme(SaleItem.class, quantity, SaleItemTemplate.PRODUCT);

		products.forEach(saleItem -> {

			try {
				Stock stock = saleItem.getStock().get();
				ProductDescription productDescription = stock.getProductDescription().get();

				final Product product = this.productPort.createProduct(this.getUserContext(),
						saleItem.getStock().get().getProductDescription().get().getProduct().get());

				final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(),
						productDescription.getProductUnit().get());

				productDescription.setProduct(product);
				productDescription.setProductUnit(productUnit);

				productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(),
						productDescription);

				final Unit unit = this.unitPort.createUnit(this.getUserContext(), stock.getUnit().get());

				stock.setUnit(unit);
				stock.setProductDescription(productDescription);
				stock.setStockStatus();

				stock = this.stockPort.createStock(this.getUserContext(), stock);
				saleItem.setStock(stock);

			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(saleItem);
		});

		return this;
	}

	public SaleBuilder withServices(final int quantity) {

		final List<SaleItem> saleItems = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SERVICE);

		saleItems.forEach(saleItem -> {

			try {

				ServiceItem serviceItem = saleItem.getServiceItem().get();
				ServiceDescription serviceDescription = serviceItem.getServiceDescription().get();
				final mz.co.grocery.core.domain.item.Service service = this.servicePort.createService(this.getUserContext(),
						serviceDescription.getService().get());

				serviceDescription.setService(service);

				serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getUserContext(),
						serviceDescription);

				final Unit unit = this.unitPort.createUnit(this.getUserContext(), serviceItem.getUnit().get());

				serviceItem.setUnit(unit);
				serviceItem.setServiceDescription(serviceDescription);

				serviceItem = this.serviceItemPort.createServiceItem(this.getUserContext(), serviceItem);

				saleItem.setServiceItem(serviceItem);
			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(saleItem);
		});

		return this;
	}

	public SaleBuilder withUnit() throws BusinessException {

		final Unit unit = this.unitPort.createUnit(this.getUserContext(),
				EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		this.sale.setUnit(unit);

		return this;
	}

	public SaleBuilder saleType(final SaleType saleType) {
		this.sale.setSaleType(saleType);
		return this;
	}

	public SaleBuilder withCustomer() throws BusinessException {
		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(this.unitPort.createUnit(this.getUserContext(),
				EntityFactory.gimme(Unit.class, UnitTemplate.VALID)));
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);
		this.sale.setCustomer(customer);
		return this;
	}

	public SaleBuilder dueDate(final LocalDate dueDate) {
		this.sale.setDueDate(dueDate);
		return this;
	}

	public Sale build() throws BusinessException {
		this.sale.calculateBilling();
		this.sale.calculateTotal();

		this.items = this.sale.getItems().get();

		if (SaleType.CASH.equals(this.sale.getSaleType())) {

			this.sale = this.cashSaleUseCase.registSale(this.getUserContext(), this.sale);

			this.items.forEach(item -> {
				this.sale.addItem(item);
			});

			return this.sale;
		}

		this.sale = this.installmentSaleUseCase.registSale(this.getUserContext(), this.sale);

		this.items.forEach(item -> {
			this.sale.addItem(item);
		});

		return this.sale;
	}
}
