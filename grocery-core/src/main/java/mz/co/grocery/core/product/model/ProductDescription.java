/**
 *
 */
package mz.co.grocery.core.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.product.dao.ProductDescriptionDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
        @NamedQuery(name = ProductDescriptionDAO.QUERY_NAME.fetchdAll, query = ProductDescriptionDAO.QUERY.fetchdAll),
        @NamedQuery(name = ProductDescriptionDAO.QUERY_NAME.count, query = ProductDescriptionDAO.QUERY.count),
        @NamedQuery(name = ProductDescriptionDAO.QUERY_NAME.fetchByDescription, query = ProductDescriptionDAO.QUERY.fetchByDescription),
        @NamedQuery(name = ProductDescriptionDAO.QUERY_NAME.fetchByUuid, query = ProductDescriptionDAO.QUERY.fetchByUuid) })
@Entity
@Table(name = "PRODUCT_DESCRIPTIONS")
public class ProductDescription extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false, length = 80)
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UNIT", nullable = false)
	private ProductUnit productUnit;

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(final Product product) {
		this.product = product;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ProductUnit getProductUnit() {
		return this.productUnit;
	}

	public void setProductUnit(final ProductUnit productUnit) {
		this.productUnit = productUnit;
	}
}
