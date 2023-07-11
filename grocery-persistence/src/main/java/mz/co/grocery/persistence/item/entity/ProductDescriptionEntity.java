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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.persistence.item.repository.ProductDescriptionRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = ProductDescriptionRepository.QUERY_NAME.fetchdAll, query = ProductDescriptionRepository.QUERY.fetchdAll),
	@NamedQuery(name = ProductDescriptionRepository.QUERY_NAME.count, query = ProductDescriptionRepository.QUERY.count),
	@NamedQuery(name = ProductDescriptionRepository.QUERY_NAME.fetchByDescription, query = ProductDescriptionRepository.QUERY.fetchByDescription),
	@NamedQuery(name = ProductDescriptionRepository.QUERY_NAME.fetchByUuid, query = ProductDescriptionRepository.QUERY.fetchByUuid) })
@Entity
@Table(name = "PRODUCT_DESCRIPTIONS")
public class ProductDescriptionEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private ProductEntity product;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false, length = 80)
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UNIT_ID", nullable = false)
	private ProductUnitEntity productUnit;

	public Optional<ProductEntity> getProduct() {
		return Optional.ofNullable(this.product);
	}

	public void setProduct(final ProductEntity product) {
		this.product = product;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Optional<ProductUnitEntity> getProductUnit() {
		return Optional.ofNullable(this.productUnit);
	}

	public void setProductUnit(final ProductUnitEntity productUnit) {
		this.productUnit = productUnit;
	}
}
