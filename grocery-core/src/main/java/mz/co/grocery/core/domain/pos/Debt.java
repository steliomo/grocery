/**
 *
 */
package mz.co.grocery.core.domain.pos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.domain.customer.Customer;

/**
 * @author Stélio Moiane
 *
 */
public class Debt {

	private Customer customer;

	private BigDecimal totalToPay;

	private BigDecimal totalPaid;

	private BigDecimal amount;

	private List<DebtItem> debtItems;

	public Debt() {
		this.debtItems = new ArrayList<>();
	}

	public Debt(final BigDecimal totalToPay, final BigDecimal totalPaid) {
		this.totalToPay = totalToPay;
		this.totalPaid = totalPaid;
		this.debtItems = new ArrayList<>();
	}

	public Optional<Customer> getCustomer() {
		return Optional.ofNullable(this.customer);
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getTotalToPay() {
		return this.totalToPay;
	}

	public void setTotalToPay(final BigDecimal totalToPay) {
		this.totalToPay = totalToPay;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalInDebt() {
		return this.totalToPay.subtract(this.totalPaid);
	}

	public Optional<List<DebtItem>> getDebtItems() {
		return Optional.ofNullable(Collections.unmodifiableList(this.debtItems));
	}

	public void addDebtItem(final DebtItem debtItem) {
		this.debtItems.add(debtItem);
	}
}
