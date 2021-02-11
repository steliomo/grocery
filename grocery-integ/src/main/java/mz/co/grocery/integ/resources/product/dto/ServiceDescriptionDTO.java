/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import mz.co.grocery.core.product.model.Service;
import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceDescriptionDTO extends GenericDTO<ServiceDescription> {

	private ServiceDTO serviceDTO;

	private String description;

	public ServiceDescriptionDTO() {
	}

	public ServiceDescriptionDTO(final ServiceDescription serviceDescription) {
		super(serviceDescription);
		this.mapper(serviceDescription);
	}

	@Override
	public void mapper(final ServiceDescription serviceDescription) {

		final Service service = serviceDescription.getService();

		if (ProxyUtil.isInitialized(service)) {
			this.serviceDTO = new ServiceDTO(service);
		}

		this.description = serviceDescription.getDescription();
	}

	@Override
	public ServiceDescription get() {

		final ServiceDescription serviceDescription = this.get(new ServiceDescription());
		serviceDescription.setService(this.serviceDTO.get());
		serviceDescription.setDescription(this.description);

		return serviceDescription;
	}

	public ServiceDTO getServiceDTO() {
		return this.serviceDTO;
	}

	public String getDescription() {
		return this.description;
	}
}
