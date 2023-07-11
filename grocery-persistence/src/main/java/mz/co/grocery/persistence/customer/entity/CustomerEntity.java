/**
 *
 */
package mz.co.grocery.persistence.customer.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.persistence.customer.repository.CustomerRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = CustomerRepository.QUERY_NAME.countByUnit, query = CustomerRepository.QUERY.countByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findByUnit, query = CustomerRepository.QUERY.findByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findRentPendingPaymentsByUnit, query = CustomerRepository.QUERY.findRentPendingPaymentsByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.countPendingPaymentsByUnit, query = CustomerRepository.QUERY.countPendingPaymentsByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit, query = CustomerRepository.QUERY.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit, query = CustomerRepository.QUERY.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findWithContractPendingPaymentByUnit, query = CustomerRepository.QUERY.findWithContractPendingPaymentByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.countCustomersWithContractPendingPaymentByUnit, query = CustomerRepository.QUERY.countCustomersWithContractPendingPaymentByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findCustomersSaleWithPendindOrIncompletePaymentByUnit, query = CustomerRepository.QUERY.findCustomersSaleWithPendindOrIncompletePaymentByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit, query = CustomerRepository.QUERY.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findWithIssuedGuidesByTypeAndUnit, query = CustomerRepository.QUERY.findWithIssuedGuidesByTypeAndUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findCustomersWithPaymentsByUnit, query = CustomerRepository.QUERY.findCustomersWithPaymentsByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit, query = CustomerRepository.QUERY.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findCustomersWithDeliveredGuidesByUnit, query = CustomerRepository.QUERY.findCustomersWithDeliveredGuidesByUnit),
	@NamedQuery(name = CustomerRepository.QUERY_NAME.findByUnitAndQuotationStatus, query = CustomerRepository.QUERY.findByUnitAndQuotationStatus) })
@Entity
@Table(name = "CUSTOMERS")
public class CustomerEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private UnitEntity unit;

	@NotEmpty
	@Column(name = "NAME", length = 80, nullable = false)
	private String name;

	@NotEmpty
	@Column(name = "ADDRESS", length = 100, nullable = false)
	private String address;

	@NotEmpty
	@Column(name = "CONTACT", length = 30, nullable = false)
	private String contact;

	@Column(name = "EMAIL", length = 30)
	private String email;

	public Optional<UnitEntity> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(final String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
}
