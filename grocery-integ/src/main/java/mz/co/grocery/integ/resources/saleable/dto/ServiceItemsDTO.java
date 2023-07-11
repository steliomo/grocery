/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemsDTO {

	private final List<ServiceItem> serviceItems;

	private final Long totalItems;

	private DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper;

	public ServiceItemsDTO(final List<ServiceItem> serviceItems, final Long totalItems, final DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper) {
		this.serviceItems = serviceItems;
		this.totalItems = totalItems;
		this.serviceItemMapper = serviceItemMapper;
	}

	public List<ServiceItemDTO> getServiceItemsDTO() {
		return this.serviceItems.stream().map(serviceItem -> this.serviceItemMapper.toDTO(serviceItem)).collect(Collectors.toList());
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
