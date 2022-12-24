/**
 *
 */
package mz.co.grocery.core.customer.model;

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

import mz.co.grocery.core.customer.dao.CustomerDAO;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = CustomerDAO.QUERY_NAME.countByUnit, query = CustomerDAO.QUERY.countByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.findByUnit, query = CustomerDAO.QUERY.findByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.findRentPendingPaymentsByUnit, query = CustomerDAO.QUERY.findRentPendingPaymentsByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.countPendingPaymentsByUnit, query = CustomerDAO.QUERY.countPendingPaymentsByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.findPendingDevolutionByUnit, query = CustomerDAO.QUERY.findPendingDevolutionByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.countPendingDevolutionByUnit, query = CustomerDAO.QUERY.countPendingDevolutionByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.findWithContractPendingPaymentByUnit, query = CustomerDAO.QUERY.findWithContractPendingPaymentByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.countCustomersWithContractPendingPaymentByUnit, query = CustomerDAO.QUERY.countCustomersWithContractPendingPaymentByUnit),
	@NamedQuery(name = CustomerDAO.QUERY_NAME.findCustomersSaleWithPendindOrIncompletePaymentByUnit, query = CustomerDAO.QUERY.findCustomersSaleWithPendindOrIncompletePaymentByUnit) })
@Entity
@Table(name = "CUSTOMERS")
public class Customer extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Grocery unit;

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

	public Grocery getUnit() {
		return this.unit;
	}

	public void setUnit(final Grocery unit) {
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
