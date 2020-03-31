/**
 *
 */
package mz.co.grocery.integ.resources.stock.dto;

import static mz.co.grocery.integ.resources.util.ProxyUtil.isInitialized;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.product.dto.ProductDescriptionDTO;

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

	public StockDTO() {
	}

	public StockDTO(final Stock stock) {
		super(stock);
		this.mapper(stock);
	}

	@Override
	public void mapper(final Stock stock) {

		final Grocery grocery = stock.getGrocery();
		if (isInitialized(grocery)) {
			this.groceryDTO = new GroceryDTO(grocery);
		}

		final ProductDescription productDescription = stock.getProductDescription();
		if (isInitialized(productDescription)) {
			this.productDescriptionDTO = new ProductDescriptionDTO(productDescription);
		}

		this.purchasePrice = stock.getPurchasePrice();

		this.salePrice = stock.getSalePrice();

		this.quantity = stock.getQuantity();

		this.expireDate = stock.getExpireDate();
	}

	@Override
	public Stock get() {
		final Stock stock = this.get(new Stock());
		stock.setGrocery(this.groceryDTO.get());
		stock.setProductDescription(this.productDescriptionDTO.get());
		stock.setPurchasePrice(this.purchasePrice);
		stock.setSalePrice(this.salePrice);
		stock.setQuantity(this.quantity);
		stock.setExpireDate(this.expireDate);

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
}
