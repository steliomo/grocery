/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.LazyInitializationException;

import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.util.EnumDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class SaleDTO extends GenericDTO<Sale> {

	private GroceryDTO groceryDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate saleDate;

	private BigDecimal billing;

	private BigDecimal total;

	private Set<SaleItemDTO> saleItemsDTO;

	private CustomerDTO customerDTO;

	private SaleType saleType;

	private BigDecimal totalPaid;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate dueDate;

	private EnumDTO saleStatus;

	public SaleDTO() {
	}

	public SaleDTO(final Sale sale) {
		super(sale);
	}

	public SaleDTO(final Sale sale, final ApplicationTranslator translator) {
		super(sale);
		this.saleStatus = new EnumDTO(sale.getSaleStatus().toString(), translator.getTranslation(sale.getSaleStatus().toString()));
	}

	@Override
	public void mapper(final Sale sale) {

		if (ProxyUtil.isInitialized(sale.getGrocery())) {
			this.groceryDTO = new GroceryDTO(sale.getGrocery());
		}

		this.saleDate = sale.getSaleDate();
		this.billing = sale.getBilling();
		this.total = sale.getTotal();

		try {
			this.saleItemsDTO = sale.getItems().stream().map(saleItem -> new SaleItemDTO(saleItem))
					.collect(Collectors.toSet());
		} catch (final LazyInitializationException e) {
		}

		if (ProxyUtil.isInitialized(sale.getCustomer())) {
			this.customerDTO = new CustomerDTO(sale.getCustomer());
		}

		this.saleType = sale.getSaleType();
		this.totalPaid = sale.getTotalPaid();
		this.dueDate = sale.getDueDate();
	}

	@Override
	public Sale get() {
		final Sale sale = this.get(new Sale());

		if (this.groceryDTO != null) {
			sale.setGrocery(this.groceryDTO.get());
		}

		sale.setSaleDate(this.saleDate);
		this.saleItemsDTO.forEach(saleItemDTO -> sale.addItem(saleItemDTO.get()));

		if (this.customerDTO != null) {
			sale.setCustomer(this.customerDTO.get());
		}

		sale.setSaleType(this.saleType);
		sale.setTotalPaid(this.totalPaid);
		sale.setDueDate(this.dueDate);

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

	public CustomerDTO getCustomerDTO() {
		return this.customerDTO;
	}

	public SaleType getSaleType() {
		return this.saleType;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public LocalDate getDueDate() {
		return this.dueDate;
	}

	public EnumDTO getSaleStatus() {
		return this.saleStatus;
	}
}
