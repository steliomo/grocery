/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemDTO extends GenericDTO<RentItem> {

	private StockDTO stockDTO;

	private ServiceItemDTO serviceItemDTO;

	private BigDecimal plannedQuantity;

	private BigDecimal plannedDays;

	private BigDecimal discount;

	private BigDecimal loadedQuantity;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate loadingDate;

	private BigDecimal returnedQuantity;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate returnDate;

	public RentItemDTO() {
	}

	public RentItemDTO(final RentItem rentItem) {
		super(rentItem);
	}

	@Override
	public void mapper(final RentItem rentItem) {
		super.get(rentItem);
		this.plannedQuantity = rentItem.getPlannedQuantity();
		this.plannedDays = rentItem.getPlannedDays();
		this.discount = rentItem.getDiscount();
		this.loadedQuantity = rentItem.getLoadedQuantity();
		this.loadingDate = rentItem.getLoadingDate();
		this.returnedQuantity = rentItem.getReturnedQuantity();
		this.returnDate = rentItem.getReturnDate();

		if (ItemType.PRODUCT.equals(rentItem.getType())) {
			this.stockDTO = new StockDTO((Stock) rentItem.getItem());
			return;
		}

		this.serviceItemDTO = new ServiceItemDTO((ServiceItem) rentItem.getItem());
	}

	@Override
	public RentItem get() {
		final RentItem rentItem = this.get(new RentItem());

		if (this.stockDTO != null || this.serviceItemDTO != null) {
			rentItem.setItem(this.stockDTO != null ? this.stockDTO.get() : this.serviceItemDTO.get());
			rentItem.setStockable();
		}

		rentItem.setPlannedQuantity(this.plannedQuantity);
		rentItem.setPlannedDays(this.plannedDays);
		rentItem.setDiscount(this.discount);

		return rentItem;
	}

	public StockDTO getStockDTO() {
		return this.stockDTO;
	}

	public ServiceItemDTO getServiceItemDTO() {
		return this.serviceItemDTO;
	}

	public BigDecimal getPlannedQuantity() {
		return this.plannedQuantity;
	}

	public BigDecimal getPlannedDays() {
		return this.plannedDays;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public BigDecimal getLoadedQuantity() {
		return this.loadedQuantity;
	}

	public LocalDate getLoadingDate() {
		return this.loadingDate;
	}

	public BigDecimal getReturnedQuantity() {
		return this.returnedQuantity;
	}

	public LocalDate getReturnDate() {
		return this.returnDate;
	}

	public String getName() {
		return this.stockDTO != null ? this.stockDTO.getProductDescriptionDTO().getName() : this.serviceItemDTO.getName();
	}
}
