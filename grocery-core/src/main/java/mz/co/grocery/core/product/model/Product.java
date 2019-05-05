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

import mz.co.grocery.core.product.dao.ProductDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ProductDAO.QUERY_NAME.findAll, query = ProductDAO.QUERY.findAll),
        @NamedQuery(name = ProductDAO.QUERY_NAME.findByName, query = ProductDAO.QUERY.findByName) })
@Entity
@Table(name = "PRODUCTS")
public class Product extends GenericEntity {

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
