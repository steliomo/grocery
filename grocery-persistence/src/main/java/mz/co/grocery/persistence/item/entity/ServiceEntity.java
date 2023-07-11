/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import mz.co.grocery.persistence.item.repository.ServiceRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ServiceRepository.QUERY_NAME.findAll, query = ServiceRepository.QUERY.findAll),
	@NamedQuery(name = ServiceRepository.QUERY_NAME.findByName, query = ServiceRepository.QUERY.findByName),
	@NamedQuery(name = ServiceRepository.QUERY_NAME.findByUnit, query = ServiceRepository.QUERY.findByUnit),
	@NamedQuery(name = ServiceRepository.QUERY_NAME.findNotInThisUnit, query = ServiceRepository.QUERY.findNotInThisUnit) })
@Entity
@Table(name = "SERVICES")
public class ServiceEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(name = "NAME", nullable = false, length = 80)
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
