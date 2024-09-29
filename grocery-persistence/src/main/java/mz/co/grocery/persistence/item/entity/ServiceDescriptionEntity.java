/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.persistence.item.repository.ServiceDescriptionRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;
import mz.co.msaude.boot.frameworks.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ServiceDescriptionRepository.QUERY_NAME.findAll, query = ServiceDescriptionRepository.QUERY.findAll),
	@NamedQuery(name = ServiceDescriptionRepository.QUERY_NAME.fetchByUuid, query = ServiceDescriptionRepository.QUERY.fetchByUuid),
	@NamedQuery(name = ServiceDescriptionRepository.QUERY_NAME.fetchByName, query = ServiceDescriptionRepository.QUERY.fetchByName) })
@Entity
@Table(name = "SERVICE_DESCRIPTIONS")
public class ServiceDescriptionEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ID", nullable = false)
	private ServiceEntity service;

	@Column(name = "DESCRIPTION", nullable = false, length = 100)
	private String description;

	public Optional<ServiceEntity> getService() {
		if (ProxyUtil.isProxy(this.service)) {
			final ServiceEntity service = new ServiceEntity();
			service.setId(ProxyUtil.getIdentifier(this.service));
			return Optional.of(service);
		}

		return Optional.ofNullable(this.service);
	}

	public void setService(final ServiceEntity service) {
		this.service = service;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
