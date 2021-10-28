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
import mz.co.grocery.integ.resources.util.ProxyUtil;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemDTO extends GenericDTO<RentItem> {

	private StockDTO stockDTO;

	private ServiceItemDTO serviceItemDTO;

	private BigDecimal quantity;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate startDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate endDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private BigDecimal discount;

	private Boolean returnable;

	private BigDecimal total;

	private BigDecimal returned;

	private BigDecimal toReturn;

	public RentItemDTO() {
	}

	public RentItemDTO(final RentItem rentItem) {
		super(rentItem);
		this.mapper(rentItem);
	}

	@Override
	public void mapper(final RentItem rentItem) {
		super.get(rentItem);
		this.quantity = rentItem.getQuantity();
		this.startDate = rentItem.getStartDate();
		this.endDate = rentItem.getEndDate();
		this.discount = rentItem.getDiscount();
		this.total = rentItem.getTotal();

		if (ProxyUtil.isInitialized(rentItem.getReturnItems())) {

			this.returned = rentItem.returned();
			this.toReturn = rentItem.toReturn();

			this.returnable = rentItem.isReturnable();

			if (ItemType.PRODUCT.equals(rentItem.getType())) {
				this.stockDTO = new StockDTO((Stock) rentItem.getItem());
				return;
			}

			this.serviceItemDTO = new ServiceItemDTO((ServiceItem) rentItem.getItem());
		}
	}

	@Override
	public RentItem get() {
		final RentItem rentItem = this.get(new RentItem());

		if (this.stockDTO != null || this.serviceItemDTO != null) {
			rentItem.setItem(this.stockDTO != null ? this.stockDTO.get() : this.serviceItemDTO.get());
			rentItem.setReturnable();
		}

		rentItem.setQuantity(this.quantity);
		rentItem.setStartDate(this.startDate);
		rentItem.setEndDate(this.endDate);
		rentItem.setDiscount(this.discount);

		rentItem.setTotal();

		return rentItem;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public StockDTO getStockDTO() {
		return this.stockDTO;
	}

	public ServiceItemDTO getServiceItemDTO() {
		return this.serviceItemDTO;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getReturned() {
		return this.returned;
	}

	public BigDecimal getToReturn() {
		return this.toReturn;
	}

	public Boolean isReturnable() {
		return this.returnable;
	}
}
