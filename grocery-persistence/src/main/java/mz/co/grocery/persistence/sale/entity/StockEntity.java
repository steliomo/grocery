/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.domain.sale.StockStatus;
import mz.co.grocery.persistence.item.entity.ProductDescriptionEntity;
import mz.co.grocery.persistence.sale.repository.StockRepositoty;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;
import mz.co.msaude.boot.frameworks.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = StockRepositoty.QUERY_NAME.findAllIds, query = StockRepositoty.QUERY.findAllIds),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchAll, query = StockRepositoty.QUERY.fetchAll),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchByUuid, query = StockRepositoty.QUERY.fetchByUuid),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchByProductDescription, query = StockRepositoty.QUERY.fetchByProductDescription),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchByGroceryAndProduct, query = StockRepositoty.QUERY.fetchByGroceryAndProduct),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchByGrocery, query = StockRepositoty.QUERY.fetchByGrocery),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchByGroceryAndSalePeriod, query = StockRepositoty.QUERY.fetchByGroceryAndSalePeriod),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchNotInThisGroceryByProduct, query = StockRepositoty.QUERY.fetchNotInThisGroceryByProduct),
	@NamedQuery(name = StockRepositoty.QUERY_NAME.fetchInAnalysisByUnitUuid, query = StockRepositoty.QUERY.fetchInAnalysisByUnitUuid) })
@Entity
@Table(name = "STOCKS")
public class StockEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private UnitEntity unit;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_DESCRIPTION_ID", nullable = false)
	private ProductDescriptionEntity productDescription;

	@NotNull
	@Column(name = "PURCHASE_PRICE", nullable = false)
	private BigDecimal purchasePrice;

	@NotNull
	@Column(name = "SALE_PRICE", nullable = false)
	private BigDecimal salePrice;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	@Column(name = "EXPIRE_DATE")
	private LocalDate expireDate;

	@NotNull
	@Column(name = "MINIMUM_STOCK", nullable = false)
	private BigDecimal minimumStock;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STOCK_STATUS", nullable = false, length = 20)
	private StockStatus stockStatus;

	@Column(name = "INVENTORY_DATE")
	private LocalDate inventoryDate;

	@Column(name = "INVENTORY_QUANTITY")
	private BigDecimal inventoryQuantity;

	@Column(name = "STOCK_UPDATE_DATE")
	private LocalDate stockUpdateDate;

	@Column(name = "STOCK_UPDATE_QUANTITY")
	private BigDecimal stockUpdateQuantity;

	@Enumerated(EnumType.STRING)
	@Column(name = "PRODUCT_STOCK_STATUS")
	private StockStatus productStockStatus;

	@NotNull
	@Column(name = "RENT_PRICE", nullable = false)
	private BigDecimal rentPrice;

	@NotNull
	@Column(name = "UNIT_PER_M2", nullable = false)
	private BigDecimal unitPerM2;

	public Optional<UnitEntity> getUnit() {
		if (ProxyUtil.isProxy(this.unit)) {
			final UnitEntity unit = new UnitEntity();
			unit.setId(ProxyUtil.getIdentifier(this.unit));
			return Optional.of(unit);
		}

		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public Optional<ProductDescriptionEntity> getProductDescription() {
		if (ProxyUtil.isProxy(this.productDescription)) {
			final ProductDescriptionEntity productDescription = new ProductDescriptionEntity();
			productDescription.setId(ProxyUtil.getIdentifier(this.productDescription));
			return Optional.of(productDescription);
		}

		return Optional.ofNullable(this.productDescription);
	}

	public void setProductDescription(final ProductDescriptionEntity productDescription) {
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

	public LocalDate getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(final LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public BigDecimal getMinimumStock() {
		return this.minimumStock;
	}

	public void setMinimumStock(final BigDecimal minimumStock) {
		this.minimumStock = minimumStock;
	}

	public StockStatus getStockStatus() {
		return this.stockStatus;
	}

	public void setStockStatus(final StockStatus stockStatus) {
		this.stockStatus = stockStatus;
	}

	public LocalDate getInventoryDate() {
		return this.inventoryDate;
	}

	public void setInventoryDate(final LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public BigDecimal getInventoryQuantity() {
		return this.inventoryQuantity;
	}

	public void setInventoryQuantity(final BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public LocalDate getStockUpdateDate() {
		return this.stockUpdateDate;
	}

	public void setStockUpdateDate(final LocalDate stockUpdateDate) {
		this.stockUpdateDate = stockUpdateDate;
	}

	public BigDecimal getStockUpdateQuantity() {
		return this.stockUpdateQuantity;
	}

	public void setStockUpdateQuantity(final BigDecimal stockUpdateQuantity) {
		this.stockUpdateQuantity = stockUpdateQuantity;
	}

	public StockStatus getProductStockStatus() {
		return this.productStockStatus;
	}

	public void setProductStockStatus(final StockStatus productStockStatus) {
		this.productStockStatus = productStockStatus;
	}

	public BigDecimal getRentPrice() {
		return this.rentPrice;
	}

	public void setRentPrice(final BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}

	public BigDecimal getUnitPerM2() {
		return this.unitPerM2;
	}

	public void setUnitPerM2(final BigDecimal unitPerM2) {
		this.unitPerM2 = unitPerM2;
	}
}
