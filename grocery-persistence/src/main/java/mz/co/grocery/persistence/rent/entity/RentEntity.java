/**
 *
 */
package mz.co.grocery.persistence.rent.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
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
import javax.validation.constraints.NotNull;

import org.hibernate.LazyInitializationException;

import mz.co.grocery.core.domain.rent.LoadStatus;
import mz.co.grocery.core.domain.rent.PaymentStatus;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.core.domain.rent.ReturnStatus;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.guide.entity.GuideEntity;
import mz.co.grocery.persistence.rent.repository.RentRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = RentRepository.QUERY_NAME.findPendinPaymentsByCustomer, query = RentRepository.QUERY.findPendinPaymentsByCustomer),
	@NamedQuery(name = RentRepository.QUERY_NAME.fetchPendingOrIncompleteRentItemToLoadByCustomer, query = RentRepository.QUERY.fetchPendingOrIncompleteRentItemToLoadByCustomer),
	@NamedQuery(name = RentRepository.QUERY_NAME.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer, query = RentRepository.QUERY.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer),
	@NamedQuery(name = RentRepository.QUERY_NAME.fetchByUuid, query = RentRepository.QUERY.fetchByUuid),
	@NamedQuery(name = RentRepository.QUERY_NAME.fetchRentsWithIssuedGuidesByTypeAndCustomer, query = RentRepository.QUERY.fetchRentsWithIssuedGuidesByTypeAndCustomer),
	@NamedQuery(name = RentRepository.QUERY_NAME.fetchRentsWithPaymentsByCustomer, query = RentRepository.QUERY.fetchRentsWithPaymentsByCustomer),
	@NamedQuery(name = RentRepository.QUERY_NAME.findByCustomerAndUnitAndStatus, query = RentRepository.QUERY.findByCustomerAndUnitAndStatus) })
@Entity
@Table(name = "RENTS")
public class RentEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private UnitEntity unit;

	@NotNull
	@Column(name = "RENT_DATE", nullable = false)
	private LocalDate rentDate;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private CustomerEntity customer;

	@Enumerated(EnumType.STRING)
	@Column(name = "RENT_STATUS", length = 15)
	private RentStatus rentStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_STATUS", nullable = false, length = 15)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "LOAD_STATUS", nullable = false, length = 15)
	private LoadStatus loadStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "RETURN_STATUS", nullable = false, length = 15)
	private ReturnStatus returnStatus;

	@NotNull
	@Column(name = "TOTAL_ESTIMATED", nullable = false)
	private BigDecimal totalEstimated;

	@NotNull
	@Column(name = "TOTAL_CALCULATED", nullable = false)
	private BigDecimal totalCalculated;

	@NotNull
	@Column(name = "TOTAL_PAID", nullable = false)
	private BigDecimal totalPaid;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rent")
	private Set<RentItemEntity> rentItems;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rent")
	private Set<RentPaymentEntity> rentPayments;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rent")
	private Set<GuideEntity> guides;

	public RentEntity() {
		this.rentStatus = RentStatus.OPENED;
		this.paymentStatus = PaymentStatus.PENDING;
		this.loadStatus = LoadStatus.PENDING;
		this.returnStatus = ReturnStatus.PENDING;
		this.totalEstimated = BigDecimal.ZERO;
		this.totalCalculated = BigDecimal.ZERO;
		this.totalPaid = BigDecimal.ZERO;
		this.rentItems = new HashSet<>();
		this.rentPayments = new HashSet<>();
		this.guides = new HashSet<>();
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

	public LocalDate getRentDate() {
		return this.rentDate;
	}

	public void setRentDate(final LocalDate rentDate) {
		this.rentDate = rentDate;
	}

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

	public RentStatus getRentStatus() {
		return this.rentStatus;
	}

	public void setRentStatus(final RentStatus rentStatus) {
		this.rentStatus = rentStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(final PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LoadStatus getLoadStatus() {
		return this.loadStatus;
	}

	public void setLoadStatus(final LoadStatus loadStatus) {
		this.loadStatus = loadStatus;
	}

	public ReturnStatus getReturnStatus() {
		return this.returnStatus;
	}

	public void setReturnStatus(final ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
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

	public Optional<Set<GuideEntity>> getGuides() {
		try {
			Optional.ofNullable(this.guides).ifPresent(guides -> guides.size());
			return Optional.ofNullable(this.guides);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setGuides(final Set<GuideEntity> guides) {
		this.guides = guides;
	}

	public Optional<Set<RentItemEntity>> getRentItems() {
		try {
			Optional.ofNullable(this.rentItems).ifPresent(items -> items.size());
			return Optional.ofNullable(this.rentItems);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public Optional<Set<RentPaymentEntity>> getRentPayments() {
		try {
			Optional.ofNullable(this.rentPayments).ifPresent(items -> items.size());
			return Optional.ofNullable(this.rentPayments);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void noRentItems() {
		this.rentItems = null;
	}

	public void noRentPayment() {
		this.rentPayments = null;
	}

	public void noGuides() {
		this.guides = null;
	}
}
