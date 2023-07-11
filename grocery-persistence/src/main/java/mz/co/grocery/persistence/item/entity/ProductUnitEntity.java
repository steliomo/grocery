/**
 *
 */
package mz.co.grocery.persistence.item.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.domain.item.ProductUnitType;
import mz.co.grocery.persistence.item.repository.ProductUnitRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = ProductUnitRepository.QUERY_NAME.findAll, query = ProductUnitRepository.QUERY.findAll) })
@Entity
@Table(name = "PRODUCT_UNITS")
public class ProductUnitEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "PRODUCT_UNIT_TYPE", nullable = false, length = 10)
	private ProductUnitType productUnitType;

	@NotNull
	@Column(name = "UNIT", nullable = false)
	private BigDecimal unit;

	public ProductUnitType getProductUnitType() {
		return this.productUnitType;
	}

	public void setProductUnitType(final ProductUnitType productUnitType) {
		this.productUnitType = productUnitType;
	}

	public BigDecimal getUnit() {
		return this.unit;
	}

	public void setUnit(final BigDecimal unit) {
		this.unit = unit;
	}
}
