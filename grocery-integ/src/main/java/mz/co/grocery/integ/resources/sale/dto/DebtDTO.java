/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;

/**
 * @author Stélio Moiane
 *
 */
public class DebtDTO {

	private CustomerDTO customer;

	private BigDecimal totalToPay;

	private BigDecimal totalPaid;

	private BigDecimal amount;

	private List<DebtItemDTO> debtItems;

	public DebtDTO() {
		this.debtItems = new ArrayList<>();
	}

	public Optional<CustomerDTO> getCustomer() {
		return Optional.ofNullable(this.customer);
	}

	public void setCustomer(final CustomerDTO customer) {
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

	public Optional<List<DebtItemDTO>> getDebtItems() {
		return Optional.ofNullable(this.debtItems);
	}

	public void addDebtItem(final DebtItemDTO debtItem) {
		this.debtItems.add(debtItem);
	}

	public BigDecimal getTotalInDebt() {
		return this.totalToPay.subtract(this.totalPaid);
	}
}
