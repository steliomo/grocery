/**
 *
 */
package mz.co.grocery.integ.resources.stock.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.stock.model.ServiceItem;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemsDTO {

	private final List<ServiceItem> serviceItems;

	private final Long totalItems;

	public ServiceItemsDTO(final List<ServiceItem> serviceItems, final Long totalItems) {
		this.serviceItems = serviceItems;
		this.totalItems = totalItems;
	}

	public List<ServiceItemDTO> getServiceItemsDTO() {
		return this.serviceItems.stream().map(serviceItem -> new ServiceItemDTO(serviceItem)).collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
