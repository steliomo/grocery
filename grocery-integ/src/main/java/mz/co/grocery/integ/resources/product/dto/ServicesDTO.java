/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.product.model.Service;

/**
 * @author Stélio Moiane
 *
 */
public class ServicesDTO {

	private final List<Service> services;

	private final Long totalItems;

	public ServicesDTO(final List<Service> services, final Long totalItems) {
		this.services = services;
		this.totalItems = totalItems;
	}

	public List<ServiceDTO> getServicesDTO() {
		return this.services.stream().map(service -> new ServiceDTO(service)).collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
