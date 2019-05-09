/**
 *
 */
package mz.co.grocery.core.stock.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.stock.dao.StockDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = StockDAO.QUERY_NAME.findAllIds, query = StockDAO.QUERY.findAllIds),
        @NamedQuery(name = StockDAO.QUERY_NAME.fetchAll, query = StockDAO.QUERY.fetchAll) })
@Entity
@Table(name = "STOCKS")
public class Stock extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_DESCRIPTION_ID", nullable = false)
	private ProductDescription productDescription;

	@NotNull
	@Column(name = "PURCHASE_PRICE", nullable = false)
	private BigDecimal purchasePrice;

	@NotNull
	@Column(name = "SALE_PRICE", nullable = false)
	private BigDecimal salePrice;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	public ProductDescription getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(final ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(final BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(final BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void updateStock(final SaleItem saleItem) {

		if (saleItem.getSaleItemValue() != BigDecimal.ZERO) {

			final BigDecimal result = saleItem.getSaleItemValue().divide(this.salePrice, 2, RoundingMode.HALF_UP);

			this.quantity = this.quantity.subtract(result);

			return;
		}

		this.quantity = this.quantity.subtract(saleItem.getQuantity());
	}
}
