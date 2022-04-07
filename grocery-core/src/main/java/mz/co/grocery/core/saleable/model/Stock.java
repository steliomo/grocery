/**
 *
 */
package mz.co.grocery.core.saleable.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.Item;
import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.item.model.ProductDescription;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = StockDAO.QUERY_NAME.findAllIds, query = StockDAO.QUERY.findAllIds),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchAll, query = StockDAO.QUERY.fetchAll),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchByUuid, query = StockDAO.QUERY.fetchByUuid),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchByProductDescription, query = StockDAO.QUERY.fetchByProductDescription),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchByGroceryAndProduct, query = StockDAO.QUERY.fetchByGroceryAndProduct),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchByGrocery, query = StockDAO.QUERY.fetchByGrocery),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchByGroceryAndSalePeriod, query = StockDAO.QUERY.fetchByGroceryAndSalePeriod),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchNotInThisGroceryByProduct, query = StockDAO.QUERY.fetchNotInThisGroceryByProduct),
	@NamedQuery(name = StockDAO.QUERY_NAME.fetchInAnalysisByUnitUuid, query = StockDAO.QUERY.fetchInAnalysisByUnitUuid) })
@Entity
@Table(name = "STOCKS")
public class Stock extends GenericEntity implements Item {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private Grocery grocery;

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

	public Grocery getGrocery() {
		return this.grocery;
	}

	public void setGrocery(final Grocery grocery) {
		this.grocery = grocery;
	}

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

	@Override
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

	public void updateStock(final SaleItem saleItem) {

		if (saleItem.getSaleItemValue() != BigDecimal.ZERO) {

			final BigDecimal result = saleItem.getSaleItemValue().divide(this.salePrice, 2, RoundingMode.HALF_UP);
			this.subtractStock(result);
			return;
		}

		this.subtractStock(saleItem.getQuantity());
	}

	public synchronized void subtractStock(final BigDecimal quantity) {

		if (StockStatus.BAD.equals(this.productStockStatus)) {
			this.quantity = this.quantity.subtract(quantity);
			this.inventoryQuantity = this.inventoryQuantity.subtract(quantity);
			this.setStockStatus();
			return;
		}

		this.quantity = this.quantity.subtract(quantity);
		this.setStockStatus();
	}

	public synchronized void addQuantity(final BigDecimal quantity) {
		this.quantity = this.quantity.add(quantity);

		if (StockStatus.BAD.equals(this.productStockStatus)) {
			this.inventoryQuantity = this.inventoryQuantity.add(quantity);
		}
	}

	public BigDecimal getMinimumStock() {
		return this.minimumStock;
	}

	public void setMinimumStock(final BigDecimal minimumStock) {
		this.minimumStock = minimumStock;
		this.setStockStatus();
	}

	public StockStatus getStockStatus() {
		return this.stockStatus;
	}

	public void setStockStatus() {

		if (this.quantity.compareTo(this.minimumStock) == BigDecimal.ONE.negate().intValue()) {
			this.stockStatus = StockStatus.LOW;
			return;
		}

		this.stockStatus = StockStatus.GOOD;
	}

	@Override
	public ItemType getType() {
		return ItemType.PRODUCT;
	}

	@Override
	public Boolean isReturnable() {
		return Boolean.TRUE;
	}

	@Override
	public String getName() {
		return this.productDescription.getName();
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

	public void setProductStockStatus() {

		if (this.quantity.compareTo(this.inventoryQuantity) == BigDecimal.ONE.intValue()) {
			this.productStockStatus = StockStatus.BAD;
			return;
		}

		this.productStockStatus = StockStatus.GOOD;
	}

	public void adjustNegativeQuantity() {

		if (this.quantity.compareTo(this.inventoryQuantity) >= BigDecimal.ZERO.intValue()) {
			return;
		}

		this.quantity = this.inventoryQuantity;
	}
}
