/**
 *
 */
package mz.co.grocery.core.domain.item;

import java.util.Optional;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */

public class ServiceDescription extends Domain {

	private Service service;

	private String description;

	public Optional<Service> getService() {
		return Optional.ofNullable(this.service);
	}

	public void setService(final Service service) {
		this.service = service;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getName() {
		return this.service.getName() + " " + this.description;
	}
}
