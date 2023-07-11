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

import mz.co.grocery.persistence.item.repository.ProductRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ProductRepository.QUERY_NAME.findAll, query = ProductRepository.QUERY.findAll),
	@NamedQuery(name = ProductRepository.QUERY_NAME.findByName, query = ProductRepository.QUERY.findByName),
	@NamedQuery(name = ProductRepository.QUERY_NAME.findByGrocery, query = ProductRepository.QUERY.findByGrocery),
	@NamedQuery(name = ProductRepository.QUERY_NAME.findNotInThisGrocery, query = ProductRepository.QUERY.findNotInThisGrocery) })
@Entity
@Table(name = "PRODUCTS")
public class ProductEntity extends GenericEntity {

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
