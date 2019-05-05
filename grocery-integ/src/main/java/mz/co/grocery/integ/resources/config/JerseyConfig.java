/**
 *
 */
package mz.co.grocery.integ.resources.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.product.ProductDescriptionResource;
import mz.co.grocery.integ.resources.product.ProductResource;
import mz.co.grocery.integ.resources.product.ProductUnitResource;

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
	}
}
