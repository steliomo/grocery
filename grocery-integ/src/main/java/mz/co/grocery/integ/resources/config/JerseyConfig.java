/**
 *
 */
package mz.co.grocery.integ.resources.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.contract.ContractResource;
import mz.co.grocery.integ.resources.customer.CustomerResource;
import mz.co.grocery.integ.resources.expense.ExpenseResource;
import mz.co.grocery.integ.resources.expense.ExpenseTypeResource;
import mz.co.grocery.integ.resources.guide.GuideResource;
import mz.co.grocery.integ.resources.inventory.InventoryResource;
import mz.co.grocery.integ.resources.item.ProductDescriptionResource;
import mz.co.grocery.integ.resources.item.ProductResource;
import mz.co.grocery.integ.resources.item.ProductUnitResource;
import mz.co.grocery.integ.resources.item.ServiceDescriptionResource;
import mz.co.grocery.integ.resources.item.ServiceResource;
import mz.co.grocery.integ.resources.payment.PaymentResource;
import mz.co.grocery.integ.resources.quotation.QuotationResource;
import mz.co.grocery.integ.resources.rent.RentResource;
import mz.co.grocery.integ.resources.report.ReportResource;
import mz.co.grocery.integ.resources.sale.SaleResource;
import mz.co.grocery.integ.resources.saleable.SaleableResource;
import mz.co.grocery.integ.resources.saleable.ServiceItemResource;
import mz.co.grocery.integ.resources.saleable.StockResource;
import mz.co.grocery.integ.resources.unit.UnitResource;
import mz.co.grocery.integ.resources.unit.UnitUserResource;
import mz.co.grocery.integ.resources.user.UserResource;

/**
 * @author Stélio Moiane
 *
 */

@Service
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {

		this.register(CORSFilter.class);
		this.register(ProductResource.class);
		this.register(ProductUnitResource.class);
		this.register(ProductDescriptionResource.class);
		this.register(StockResource.class);
		this.register(SaleResource.class);
		this.register(UserResource.class);
		this.register(UnitResource.class);
		this.register(UnitUserResource.class);
		this.register(InventoryResource.class);
		this.register(ExpenseTypeResource.class);
		this.register(ExpenseResource.class);
		this.register(ServiceResource.class);
		this.register(ServiceDescriptionResource.class);
		this.register(ServiceItemResource.class);
		this.register(SaleableResource.class);
		this.register(PaymentResource.class);
		this.register(RentResource.class);
		this.register(CustomerResource.class);
		this.register(ContractResource.class);

		this.register(ApiExceptionMapper.class);
		this.register(BusinessExceptionMapper.class);
		this.register(NoResultExceptionMapper.class);

		this.register(ReportResource.class);
		this.register(GuideResource.class);
		this.register(QuotationResource.class);

		this.register(MultiPartFeature.class);
	}
}
