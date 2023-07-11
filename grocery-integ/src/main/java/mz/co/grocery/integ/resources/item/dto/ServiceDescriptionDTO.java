/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionDTO extends GenericDTO {

	private ServiceDTO serviceDTO;

	private String description;

	public Optional<ServiceDTO> getServiceDTO() {
		return Optional.ofNullable(this.serviceDTO);
	}

	public void setServiceDTO(final ServiceDTO serviceDTO) {
		this.serviceDTO = serviceDTO;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getName() {
		return this.serviceDTO.getName() + " " + this.description;
	}
}
