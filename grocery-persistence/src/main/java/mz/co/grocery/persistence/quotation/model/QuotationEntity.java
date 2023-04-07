/**
 *
 */
package mz.co.grocery.persistence.quotation.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.domain.quotation.QuotationStatus;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
//@Entity
//@Table(name = "QUOTATIONS")
public class QuotationEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, length = 15)
	private QuotationType type;

	@Column(name = "ISSUE_DATE", nullable = false)
	private LocalDate issueDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 15)
	private QuotationStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Grocery unit;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quotation")
	private Set<QuotationItemEntity> items;

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return this.customer;
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

	public Grocery getUnit() {
		return this.unit;
	}

	public void setUnit(final Grocery unit) {
		this.unit = unit;
	}

	public Set<QuotationItemEntity> getItems() {
		return this.items;
	}
}
