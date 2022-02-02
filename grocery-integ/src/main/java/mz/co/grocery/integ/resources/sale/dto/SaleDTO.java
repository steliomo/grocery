/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class SaleDTO extends GenericDTO<Sale> {

	private GroceryDTO groceryDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate saleDate;

	private BigDecimal billing = BigDecimal.ZERO;

	private BigDecimal total = BigDecimal.ZERO;

	private Set<SaleItemDTO> saleItemsDTO;

	public SaleDTO() {
	}

	public SaleDTO(final Sale sale) {
		super(sale);
	}

	@Override
	public void mapper(final Sale sale) {
		this.groceryDTO = new GroceryDTO(sale.getGrocery());
		this.saleDate = sale.getSaleDate();
		this.billing = sale.getBilling();
		this.total = sale.getTotal();
		this.saleItemsDTO = sale.getItems().stream().map(saleItem -> new SaleItemDTO(saleItem))
				.collect(Collectors.toSet());
	}

	@Override
	public Sale get() {
		final Sale sale = this.get(new Sale());
		sale.setGrocery(this.groceryDTO.get());
		sale.setSaleDate(this.saleDate);
		this.saleItemsDTO.forEach(saleItemDTO -> sale.addItem(saleItemDTO.get()));

		return sale;
	}

	public GroceryDTO getGroceryDTO() {
		return this.groceryDTO;
	}

	public void setGroceryDTO(final GroceryDTO groceryDTO) {
		this.groceryDTO = groceryDTO;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(final LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public void setBilling(final BigDecimal profit) {
		this.billing = profit;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	public Set<SaleItemDTO> getSaleItemsDTO() {
		return this.saleItemsDTO;
	}
}
