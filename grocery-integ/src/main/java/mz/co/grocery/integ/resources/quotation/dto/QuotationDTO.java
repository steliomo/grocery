/**
 *
 */
package mz.co.grocery.integ.resources.quotation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.domain.quotation.QuotationStatus;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationDTO extends GenericDTO {

	private CustomerDTO customerDTO;

	private UnitDTO unitDTO;

	private String name;

	private QuotationType type;

	private LocalDate issueDate;

	private QuotationStatus status;

	private BigDecimal totalValue;

	private Set<QuotationItemDTO> items;

	public void setCustomerDTO(final CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public Optional<CustomerDTO> getCustomerDTO() {
		return Optional.ofNullable(this.customerDTO);
	}

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unit) {
		this.unitDTO = unit;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public QuotationType getType() {
		return this.type;
	}

	public void setType(final QuotationType type) {
		this.type = type;
	}

	public LocalDate getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(final LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public QuotationStatus getStatus() {
		return this.status;
	}

	public void setStatus(final QuotationStatus status) {
		this.status = status;
	}

	public BigDecimal getTotalValue() {
		return this.totalValue;
	}

	public void setTotalValue(final BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Set<QuotationItemDTO> getItems() {
		return Collections.unmodifiableSet(Optional.ofNullable(this.items).orElse(new HashSet<>()));
	}

	public void addItem(final QuotationItemDTO quotationItemDTO) {
		this.items.add(quotationItemDTO);
	}
}
