/**
 *
 */
package mz.co.grocery.core.item.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.item.dao.ServiceDescriptionDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ServiceDescriptionDAO.QUERY_NAME.findAll, query = ServiceDescriptionDAO.QUERY.findAll),
	@NamedQuery(name = ServiceDescriptionDAO.QUERY_NAME.fetchByUuid, query = ServiceDescriptionDAO.QUERY.fetchByUuid),
	@NamedQuery(name = ServiceDescriptionDAO.QUERY_NAME.fetchByName, query = ServiceDescriptionDAO.QUERY.fetchByName) })
@Entity
@Table(name = "SERVICE_DESCRIPTIONS")
public class ServiceDescription extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ID", nullable = false)
	private Service service;

	@Column(name = "DESCRIPTION", nullable = false, length = 100)
	private String description;

	public Service getService() {
		return this.service;
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
