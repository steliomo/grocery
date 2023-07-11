/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.item.dto.ProductDescriptionDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;

/**
 * @author Stélio Moiane
 *
 */
public class StockDTO extends GenericDTO {

	private UnitDTO unitDTO;

	private ProductDescriptionDTO productDescriptionDTO;

	private BigDecimal purchasePrice;

	private BigDecimal salePrice;

	private BigDecimal quantity;

	private LocalDate expireDate;

	private BigDecimal minimumStock;

	private LocalDate inventoryDate;

	private BigDecimal inventoryQuantity;

	private LocalDate stockUpdateDate;

	private BigDecimal stockUpdateQuantity;

	private BigDecimal rentPrice;

	private BigDecimal unitPerM2;

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public Optional<ProductDescriptionDTO> getProductDescriptionDTO() {
		return Optional.ofNullable(this.productDescriptionDTO);
	}

	public void setProductDescriptionDTO(final ProductDescriptionDTO productDescriptionDTO) {
		this.productDescriptionDTO = productDescriptionDTO;
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
