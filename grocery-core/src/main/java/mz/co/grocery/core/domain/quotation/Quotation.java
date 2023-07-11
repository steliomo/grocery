/**
 *
 */
package mz.co.grocery.core.domain.quotation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class Quotation extends Domain {

	private String name;

	private Customer customer;

	private QuotationType type;

	private LocalDate issueDate;

	private QuotationStatus status;

	private Unit unit;

	private BigDecimal totalValue;

	private BigDecimal discount;

	private Set<QuotationItem> items;

	public Quotation() {
		this.items = new HashSet<>();
		this.discount = BigDecimal.ZERO;
	}

	public Quotation(final QuotationType type, final QuotationStatus status) {
		this.type = type;
		this.status = status;
		this.items = new HashSet<>();
		this.discount = BigDecimal.ZERO;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Optional<Customer> getCustomer() {
		return Optional.ofNullable(this.customer);
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public QuotationType getType() {
		return this.type;
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

	public void setToPendingStatus() throws BusinessException {
		if (this.status == null) {
			this.status = QuotationStatus.PENDING;
			return;
		}

		throw new BusinessException("quotation.cannot.be.pendig.again");
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public Set<QuotationItem> getItems() {
		return Collections.unmodifiableSet(Optional.ofNullable(this.items).orElse(new HashSet<>()));
	}

	public void addItem(final QuotationItem quotationItem) {
		this.items.add(quotationItem);
	}

	public boolean hasItems() {
		if (this.items == null) {
			return Boolean.FALSE;
		}
		return this.items != null && !this.items.isEmpty();
	}

	public BigDecimal getTotalValue() {
		return this.totalValue;
	}

	public void calculateTotal() {
		this.totalValue = this.items.stream().map(QuotationItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(this.discount);
	}

	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setType(final QuotationType type) {
		this.type = type;
	}

	public void setStatus(final QuotationStatus status) {
		this.status = status;
	}

	public void setTotalValue(final BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
}
