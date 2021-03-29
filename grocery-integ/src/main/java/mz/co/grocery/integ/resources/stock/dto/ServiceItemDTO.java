/**
 *
 */
package mz.co.grocery.integ.resources.stock.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.product.dto.ServiceDescriptionDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemDTO extends GenericDTO<ServiceItem> {

	private ServiceDescriptionDTO serviceDescriptionDTO;

	private GroceryDTO unitDTO;

	private BigDecimal salePrice;

	public ServiceItemDTO() {
	}

	public ServiceItemDTO(final ServiceItem serviceItem) {
		super(serviceItem);
		this.mapper(serviceItem);
	}

	@Override
	public void mapper(final ServiceItem serviceItem) {

		final ServiceDescription serviceDescription = serviceItem.getServiceDescription();

		if (ProxyUtil.isInitialized(serviceDescription)) {
			this.serviceDescriptionDTO = new ServiceDescriptionDTO(serviceDescription);
		}

		final Grocery unit = serviceItem.getUnit();
		if (ProxyUtil.isInitialized(unit)) {
			this.unitDTO = new GroceryDTO(unit);
		}

		this.salePrice = serviceItem.getSalePrice();
	}

	@Override
	public ServiceItem get() {

		final ServiceItem serviceItem = this.get(new ServiceItem());
		serviceItem.setServiceDescription(this.serviceDescriptionDTO.get());
		serviceItem.setUnit(this.unitDTO.get());
		serviceItem.setSalePrice(this.salePrice);

		return serviceItem;
	}

	public ServiceDescriptionDTO getServiceDescriptionDTO() {
		return this.serviceDescriptionDTO;
	}

	public GroceryDTO getUnitDTO() {
		return this.unitDTO;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public String getName() {
		return this.serviceDescriptionDTO.getName();
	}

	public void setUnitDTO(final GroceryDTO unitDTO) {
		this.unitDTO = unitDTO;
	}
}
