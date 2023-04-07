/**
 *
 */
package mz.co.grocery.core.domain.quotation;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class Quotation {

	private Long id;

	private String uuid;

	private Customer customer;

	private QuotationType type;

	private LocalDate issueDate;

	private QuotationStatus status;

	private Grocery unit;

	private Set<QuotationItem> items;

	public Quotation() {
	}

	public Quotation(final QuotationType type, final QuotationStatus status) {
		this.type = type;
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return this.customer;
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

	public Grocery getUnit() {
		return this.unit;
	}

	public Set<QuotationItem> getItems() {
		return Collections.unmodifiableSet(this.items);
	}

	public void setItems(final Set<QuotationItem> items) {
		this.items = items;
	}

	public boolean hasItems() {
		if (this.items == null) {
			return Boolean.FALSE;
		}

		return this.items != null && !this.items.isEmpty();
	}
}
