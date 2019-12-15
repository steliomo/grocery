/**
 *
 */
package mz.co.grocery.integ.resources.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.grocery.GroceryResource;
import mz.co.grocery.integ.resources.grocery.GroceryUserResource;
import mz.co.grocery.integ.resources.inventory.InventoryResource;
import mz.co.grocery.integ.resources.product.ProductDescriptionResource;
import mz.co.grocery.integ.resources.product.ProductResource;
import mz.co.grocery.integ.resources.product.ProductUnitResource;
import mz.co.grocery.integ.resources.sale.SaleResource;
import mz.co.grocery.integ.resources.stock.StockResource;
import mz.co.grocery.integ.resources.user.UserResource;

/**
 * @author Stélio Moiane
 *
 */
@Service
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		this.register(CORSFilter.class);
		this.register(ExceptionHandler.class);
		this.register(ProductResource.class);
		this.register(ProductUnitResource.class);
		this.register(ProductDescriptionResource.class);
		this.register(StockResource.class);
		this.register(SaleResource.class);
		this.register(UserResource.class);
		this.register(GroceryResource.class);
		this.register(GroceryUserResource.class);
		this.register(InventoryResource.class);
	}
}
