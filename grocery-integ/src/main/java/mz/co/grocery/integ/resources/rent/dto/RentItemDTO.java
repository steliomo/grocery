/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemDTO extends GenericDTO {

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

	public Optional<StockDTO> getStockDTO() {
		return Optional.ofNullable(this.stockDTO);
	}

	public void setStockDTO(final StockDTO stockDTO) {
		this.stockDTO = stockDTO;
	}

	public Optional<ServiceItemDTO> getServiceItemDTO() {
		return Optional.ofNullable(this.serviceItemDTO);
	}

	public void setServiceItemDTO(final ServiceItemDTO serviceItemDTO) {
		this.serviceItemDTO = serviceItemDTO;
	}

	public BigDecimal getPlannedQuantity() {
		return this.plannedQuantity;
	}

	public void setPlannedQuantity(final BigDecimal plannedQuantity) {
		this.plannedQuantity = plannedQuantity;
	}

	public BigDecimal getPlannedDays() {
		return this.plannedDays;
	}

	public void setPlannedDays(final BigDecimal plannedDays) {
		this.plannedDays = plannedDays;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getLoadedQuantity() {
		return this.loadedQuantity;
	}

	public void setLoadedQuantity(final BigDecimal loadedQuantity) {
		this.loadedQuantity = loadedQuantity;
	}

	public LocalDate getLoadingDate() {
		return this.loadingDate;
	}

	public void setLoadingDate(final LocalDate loadingDate) {
		this.loadingDate = loadingDate;
	}

	public BigDecimal getReturnedQuantity() {
		return this.returnedQuantity;
	}

	public void setReturnedQuantity(final BigDecimal returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	public LocalDate getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(final LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getName() {
		return this.stockDTO != null ? this.stockDTO.getProductDescriptionDTO().get().getName() : this.serviceItemDTO.getName();
	}
}
