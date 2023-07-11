/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionsDTO {

	private final List<ServiceDescription> serviceDescriptions;

	private final Long totalItems;

	private DTOMapper<ServiceDescriptionDTO, ServiceDescription> serviceDescriptionMapper;

	public ServiceDescriptionsDTO(final List<ServiceDescription> serviceDescriptions, final Long totalItems,
			final DTOMapper<ServiceDescriptionDTO, ServiceDescription> serviceDescriptionMapper) {
		this.serviceDescriptions = serviceDescriptions;
		this.totalItems = totalItems;
		this.serviceDescriptionMapper = serviceDescriptionMapper;
	}

	public List<ServiceDescriptionDTO> getServiceDescriptionsDTO() {
		return this.serviceDescriptions.stream().map(serviceDescription -> this.serviceDescriptionMapper.toDTO(serviceDescription))
				.collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
