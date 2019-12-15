/**
 *
 */
package mz.co.grocery.integ.resources.stock.dto;

import static mz.co.grocery.integ.resources.util.ProxyUtil.isInitialized;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.product.dto.ProductDescriptionDTO;

/**
 * @author Stélio Moiane
 *
 */
public class StockDTO extends GenericDTO<Stock> {

	private ProductDescriptionDTO productDescriptionDTO;

	private BigDecimal purchasePrice;

	private BigDecimal salePrice;

	private BigDecimal quantity;

	private LocalDate expireDate;

	public StockDTO(final Stock stock) {
		super(stock);
		this.mapper(stock);
	}

	@Override
	public void mapper(final Stock stock) {
		final ProductDescription productDescription = stock.getProductDescription();

		if (isInitialized(productDescription)) {
			this.productDescriptionDTO = new ProductDescriptionDTO(productDescription);
		}

		this.purchasePrice = stock.getPurchasePrice();

		this.salePrice = stock.getSalePrice();

		this.quantity = stock.getQuantity();

		this.expireDate = stock.getExpireDate();
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
}
