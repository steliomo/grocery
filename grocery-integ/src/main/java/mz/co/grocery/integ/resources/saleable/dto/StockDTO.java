/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.ProductDescription;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.item.dto.ProductDescriptionDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class StockDTO extends GenericDTO<Stock> {

	private GroceryDTO groceryDTO;

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

	public StockDTO() {
	}

	public StockDTO(final Stock stock) {
		super(stock);
	}

	@Override
	public void mapper(final Stock stock) {

		final Grocery grocery = stock.getGrocery();
		if (ProxyUtil.isInitialized(grocery)) {
			this.groceryDTO = new GroceryDTO(grocery);
		}

		final ProductDescription productDescription = stock.getProductDescription();
		if (ProxyUtil.isInitialized(productDescription)) {
			this.productDescriptionDTO = new ProductDescriptionDTO(productDescription);
		}

		this.purchasePrice = stock.getPurchasePrice();

		this.salePrice = stock.getSalePrice();

		this.quantity = stock.getQuantity();

		this.expireDate = stock.getExpireDate();

		this.minimumStock = stock.getMinimumStock();

		this.inventoryDate = stock.getInventoryDate();

		this.inventoryQuantity = stock.getInventoryQuantity();

		this.stockUpdateDate = stock.getStockUpdateDate();

		this.stockUpdateQuantity = stock.getStockUpdateQuantity();

		this.rentPrice = stock.getRentPrice();

		this.unitPerM2 = stock.getUnitPerM2();
	}

	@Override
	public Stock get() {
		final Stock stock = this.get(new Stock());

		if (this.groceryDTO != null) {
			stock.setGrocery(this.groceryDTO.get());
		}

		stock.setProductDescription(this.productDescriptionDTO.get());
		stock.setPurchasePrice(this.purchasePrice);
		stock.setSalePrice(this.salePrice);
		stock.setQuantity(this.quantity);
		stock.setExpireDate(this.expireDate);
		stock.setMinimumStock(this.minimumStock);
		stock.setInventoryDate(this.inventoryDate);
		stock.setInventoryQuantity(this.inventoryQuantity);
		stock.setStockUpdateDate(this.stockUpdateDate);
		stock.setStockUpdateDate(this.stockUpdateDate);
		stock.setRentPrice(this.rentPrice);
		stock.setUnitPerM2(this.unitPerM2);

		return stock;
	}

	public ProductDescriptionDTO getProductDescriptionDTO() {
		return this.productDescriptionDTO;
	}

	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public LocalDate getExpireDate() {
		return this.expireDate;
	}

	public GroceryDTO getGroceryDTO() {
		return this.groceryDTO;
	}

	public BigDecimal getMinimumStock() {
		return this.minimumStock;
	}

	public void setGroceryDTO(final GroceryDTO groceryDTO) {
		this.groceryDTO = groceryDTO;
	}

	public LocalDate getInventoryDate() {
		return this.inventoryDate;
	}

	public BigDecimal getInventoryQuantity() {
		return this.inventoryQuantity;
	}

	public LocalDate getStockUpdateDate() {
		return this.stockUpdateDate;
	}

	public BigDecimal getStockUpdateQuantity() {
		return this.stockUpdateQuantity;
	}

	public BigDecimal getRentPrice() {
		return this.rentPrice;
	}

	public BigDecimal getUnitPerM2() {
		return this.unitPerM2;
	}
}
