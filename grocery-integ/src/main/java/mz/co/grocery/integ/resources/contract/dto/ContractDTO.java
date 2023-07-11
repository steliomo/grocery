/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.common.EnumDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ContractDTO extends GenericDTO {

	private EnumDTO contractType;

	private String description;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate startDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate endDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate referencePaymentDate;

	private BigDecimal monthlyPayment;

	private BigDecimal totalPaid;

	private UnitDTO unitDTO;

	private CustomerDTO customerDTO;

	public EnumDTO getContractType() {
		return this.contractType;
	}

	public void setContractType(final EnumDTO contractType) {
		this.contractType = contractType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getReferencePaymentDate() {
		return this.referencePaymentDate;
	}

	public void setReferencePaymentDate(final LocalDate referencePaymentDate) {
		this.referencePaymentDate = referencePaymentDate;
	}

	public BigDecimal getMonthlyPayment() {
		return this.monthlyPayment;
	}

	public void setMonthlyPayment(final BigDecimal monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

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
}
