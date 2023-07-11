/**
 *
 */
package mz.co.grocery.persistence.quotation;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.quotation.in.IssueQuotationUseCase;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.QuotationTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class IssueQuotationUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private IssueQuotationUseCase issueQuotationUseCase;

	@Inject
	private UnitPort unitPort;

	@Inject
	private StockPort stockService;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	@Inject
	private CustomerPort customerPort;

	@Test
	public void shouldIssueRentQuotation() throws BusinessException {

		Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit = this.unitPort.createUnit(this.getUserContext(), unit);

		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(unit);
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);

		final Quotation quotation = EntityFactory.gimme(Quotation.class, QuotationTemplate.RENT_WITH_ITEMS);
		quotation.setCustomer(customer);
		quotation.setUnit(unit);

		BigDecimal expected = BigDecimal.ZERO;

		for (final QuotationItem quotationItem : quotation.getItems()) {
			expected = expected.add(quotationItem.getTotal());
			Stock stock = (Stock) quotationItem.getItem().get();
			stock.setUnit(unit);

			stock = this.createStock(stock);
			quotationItem.setItem(stock);
		}

		this.issueQuotationUseCase.issueQuotation(this.getUserContext(), quotation);

		Assert.assertNotNull(quotation.getUuid());
		Assert.assertEquals(expected.subtract(quotation.getDiscount()), quotation.getTotalValue());
	}

	@Test
	public void shouldIssueSaleQuotation() throws BusinessException {

		Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit = this.unitPort.createUnit(this.getUserContext(), unit);

		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(unit);
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);

		final Quotation quotation = EntityFactory.gimme(Quotation.class, QuotationTemplate.SALE_WITH_ITEMS);
		quotation.setCustomer(customer);
		quotation.setUnit(unit);

		BigDecimal expected = BigDecimal.ZERO;

		for (final QuotationItem quotationItem : quotation.getItems()) {
			expected = expected.add(quotationItem.getTotal());
			Stock stock = (Stock) quotationItem.getItem().get();
			stock.setUnit(unit);

			stock = this.createStock(stock);
			quotationItem.setItem(stock);
		}

		this.issueQuotationUseCase.issueQuotation(this.getUserContext(), quotation);

		Assert.assertNotNull(quotation.getUuid());
		Assert.assertEquals(expected.subtract(quotation.getDiscount()), quotation.getTotalValue());
	}

	private Stock createStock(final Stock stock) throws BusinessException {

		ProductDescription productDescription = stock.getProductDescription().get();
		final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());
		final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(), productDescription.getProductUnit().get());
		productDescription.setProduct(product);
		productDescription.setProductUnit(productUnit);

		productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(), productDescription);
		stock.setProductDescription(productDescription);
		stock.setStockStatus();

		return this.stockService.createStock(this.getUserContext(), stock);
	}
}