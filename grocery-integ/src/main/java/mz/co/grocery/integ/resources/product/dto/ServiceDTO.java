/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import mz.co.grocery.core.product.model.Service;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDTO extends GenericDTO<Service> {

	private String name;

	public ServiceDTO() {
	}

	public ServiceDTO(final Service service) {
		super(service);
		this.mapper(service);
	}

	public ServiceDTO(final String serviceUuid) {
		this.setUuid(serviceUuid);
	}

	@Override
	public void mapper(final Service service) {
		this.name = service.getName();
	}

	@Override
	public Service get() {
		final Service service = this.get(new Service());
		service.setName(this.name);
		return service;
	}

	public String getName() {
		return this.name;
	}
}
