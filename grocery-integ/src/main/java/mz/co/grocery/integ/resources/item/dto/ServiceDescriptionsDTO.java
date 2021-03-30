/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.item.model.ServiceDescription;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionsDTO {

	private final List<ServiceDescription> serviceDescriptions;

	private final Long totalItems;

	public ServiceDescriptionsDTO(final List<ServiceDescription> serviceDescriptions, final Long totalItems) {
		this.serviceDescriptions = serviceDescriptions;
		this.totalItems = totalItems;
	}

	public List<ServiceDescriptionDTO> getServiceDescriptionsDTO() {
		return this.serviceDescriptions.stream().map(serviceDescription -> new ServiceDescriptionDTO(serviceDescription))
				.collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
