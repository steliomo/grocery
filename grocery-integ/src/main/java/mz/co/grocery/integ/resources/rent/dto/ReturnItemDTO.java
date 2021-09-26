/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.rent.model.ReturnItem;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ReturnItemDTO extends GenericDTO<ReturnItem> {

	private RentItemDTO rentItemDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate returnDate;

	private BigDecimal quantity;

	public ReturnItemDTO() {
	}

	public ReturnItemDTO(final ReturnItem returnItem) {
		super(returnItem);
		this.mapper(returnItem);
	}

	@Override
	public void mapper(final ReturnItem returnItem) {

		if (ProxyUtil.isInitialized(returnItem.getRentItem())) {
			this.rentItemDTO = new RentItemDTO(returnItem.getRentItem());
		}

		this.returnDate = returnItem.getReturnDate();

		this.quantity = returnItem.getQuantity();
	}

	@Override
	public ReturnItem get() {
		final ReturnItem returnItem = this.get(new ReturnItem());
		returnItem.setRentItem(this.rentItemDTO.get());
		returnItem.setReturnDate(this.returnDate);
		returnItem.setQuantity(this.quantity);

		return returnItem;
	}

	public RentItemDTO getRentItemDTO() {
		return this.rentItemDTO;
	}

	public LocalDate getReturnDate() {
		return this.returnDate;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

}
