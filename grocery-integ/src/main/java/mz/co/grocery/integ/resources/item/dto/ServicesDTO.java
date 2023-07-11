/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.item.Service;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class ServicesDTO {

	private final List<Service> services;

	private final Long totalItems;

	private DTOMapper<ServiceDTO, Service> serviceMapper;

	public ServicesDTO(final List<Service> services, final Long totalItems, final DTOMapper<ServiceDTO, Service> serviceMapper) {
		this.services = services;
		this.totalItems = totalItems;
		this.serviceMapper = serviceMapper;
	}

	public List<ServiceDTO> getServicesDTO() {
		return this.services.stream().map(service -> this.serviceMapper.toDTO(service)).collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
