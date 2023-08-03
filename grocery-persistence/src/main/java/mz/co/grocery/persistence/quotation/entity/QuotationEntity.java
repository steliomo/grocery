/**
 *
 */
package mz.co.grocery.persistence.quotation.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.LazyInitializationException;

import mz.co.grocery.core.domain.quotation.QuotationStatus;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.quotation.repository.QuotationRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = QuotationRepository.QUERY_NAME.fetchQuotationsByCustomer, query = QuotationRepository.QUERY.fetchQuotationsByCustomer) })
@Entity
@Table(name = "QUOTATIONS")
public class QuotationEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private CustomerEntity customer;

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
	private UnitEntity unit;

	@Column(name = "TOTAL_VALUE", nullable = false)
	private BigDecimal totalValue;

	@Column(name = "DISCOUNT", nullable = false)
	private BigDecimal discount = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quotation")
	private Set<QuotationItemEntity> items;

	public Optional<CustomerEntity> getCustomer() {
		try {
			Optional.ofNullable(this.customer).ifPresent(customer -> customer.getName());
			return Optional.ofNullable(this.customer);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setCustomer(final CustomerEntity customer) {
		this.customer = customer;
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

	public Optional<UnitEntity> getUnit() {
		try {
			Optional.ofNullable(this.unit).ifPresent(unit -> unit.getName());
			return Optional.ofNullable(this.unit);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public BigDecimal getTotalValue() {
		return this.totalValue;
	}

	public void setTotalValue(final BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	public Optional<Set<QuotationItemEntity>> getItems() {
		return Optional.ofNullable(this.items);
	}

	public void setItems(final Set<QuotationItemEntity> items) {
		this.items = items;
	}
}
