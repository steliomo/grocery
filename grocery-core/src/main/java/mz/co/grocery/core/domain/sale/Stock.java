/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.item.Item;
import mz.co.grocery.core.domain.item.ItemType;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */

public class Stock extends Domain implements Item {

	private Unit unit;

	private ProductDescription productDescription;

	private BigDecimal purchasePrice;

	private BigDecimal salePrice;

	private BigDecimal quantity;

	private LocalDate expireDate;

	private BigDecimal minimumStock;

	private StockStatus stockStatus;

	private LocalDate inventoryDate;

	private BigDecimal inventoryQuantity;

	private LocalDate stockUpdateDate;

	private BigDecimal stockUpdateQuantity;

	private StockStatus productStockStatus;

	private BigDecimal rentPrice;

	private BigDecimal unitPerM2;

	public Stock() {
		this.inventoryQuantity = BigDecimal.ZERO;
		this.stockUpdateQuantity = BigDecimal.ZERO;
		this.rentPrice = BigDecimal.ZERO;
		this.unitPerM2 = BigDecimal.ZERO;
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public Optional<ProductDescription> getProductDescription() {
		return Optional.ofNullable(this.productDescription);
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

		if(minimumStock == null) {
			return;
		}

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
	public Boolean isStockable() {
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

	@Override
	public BigDecimal getRentPrice() {
		return this.rentPrice;
	}

	public void setRentPrice(final BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}

	public void setUnitPerM2(final BigDecimal unitPerM2) {
		this.unitPerM2 = unitPerM2;
	}

	public BigDecimal getUnitPerM2() {
		return this.unitPerM2;
	}

	public void setStockStatus(final StockStatus stockStatus) {
		this.stockStatus = stockStatus;
	}

	public void setProductStockStatus(final StockStatus productStockStatus) {
		this.productStockStatus = productStockStatus;
	}
}
