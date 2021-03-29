/**
 *
 */
package mz.co.grocery.core.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import mz.co.grocery.core.product.dao.ServiceDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ServiceDAO.QUERY_NAME.findAll, query = ServiceDAO.QUERY.findAll),
	@NamedQuery(name = ServiceDAO.QUERY_NAME.findByName, query = ServiceDAO.QUERY.findByName),
	@NamedQuery(name = ServiceDAO.QUERY_NAME.findByUnit, query = ServiceDAO.QUERY.findByUnit),
	@NamedQuery(name = ServiceDAO.QUERY_NAME.findNotInThisUnit, query = ServiceDAO.QUERY.findNotInThisUnit) })
@Entity
@Table(name = "SERVICES")
public class Service extends GenericEntity {

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
