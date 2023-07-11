/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.common.EnumDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.guide.dto.GuideDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentDTO extends GenericDTO {

	private UnitDTO unitDTO;

	private CustomerDTO customerDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate rentDate;

	private BigDecimal totalEstimated;

	private BigDecimal totalCalculated;

	private BigDecimal totalPaid;

	private BigDecimal totalToPay;

	private BigDecimal totalToRefund;

	private EnumDTO paymentStatus;

	private List<RentItemDTO> rentItemsDTO;

	private List<RentPaymentDTO> rentPaymentsDTO;

	private List<GuideDTO> guidesDTO;

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public Optional<CustomerDTO> getCustomerDTO() {
		return Optional.ofNullable(this.customerDTO);
	}

	public void setCustomerDTO(final CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public LocalDate getRentDate() {
		return this.rentDate;
	}

	public void setRentDate(final LocalDate rentDate) {
		this.rentDate = rentDate;
	}

	public BigDecimal getTotalEstimated() {
		return this.totalEstimated;
	}

	public void setTotalEstimated(final BigDecimal totalEstimated) {
		this.totalEstimated = totalEstimated;
	}

	public BigDecimal getTotalCalculated() {
		return this.totalCalculated;
	}

	public void setTotalCalculated(final BigDecimal totalCalculated) {
		this.totalCalculated = totalCalculated;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getTotalToPay() {
		return this.totalToPay;
	}

	public void setTotalToPay(final BigDecimal totalToPay) {
		this.totalToPay = totalToPay;
	}

	public BigDecimal getTotalToRefund() {
		return this.totalToRefund;
	}

	public void setTotalToRefund(final BigDecimal totalToRefund) {
		this.totalToRefund = totalToRefund;
	}

	public EnumDTO getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(final EnumDTO paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public List<RentItemDTO> getRentItemsDTO() {
		return this.rentItemsDTO;
	}

	public void addRentItemDTO(final RentItemDTO rentItemDTO) {
		this.rentItemsDTO.add(rentItemDTO);
	}

	public void addRentPaymentDTO(final RentPaymentDTO rentPaymentDTO) {
		this.rentPaymentsDTO.add(rentPaymentDTO);
	}

	public List<RentPaymentDTO> getRentPaymentsDTO() {
		if (this.rentPaymentsDTO == null) {
			return this.rentPaymentsDTO;
		}

		this.rentPaymentsDTO.sort(Comparator.comparing(RentPaymentDTO::getId));
		return this.rentPaymentsDTO;
	}

	public void addGuideDTO(final GuideDTO guideDTO) {
		this.guidesDTO.add(guideDTO);
	}

	public List<GuideDTO> getGuidesDTO() {
		if (this.guidesDTO == null) {
			return this.guidesDTO;
		}

		this.guidesDTO.sort(Comparator.comparing(GuideDTO::getId));
		return this.guidesDTO;
	}
}
