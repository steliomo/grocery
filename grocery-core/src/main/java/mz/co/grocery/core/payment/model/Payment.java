/**
 *
 */
package mz.co.grocery.core.payment.model;

import java.math.BigDecimal;

import mz.co.grocery.core.payment.Discount;

/**
 * @author Stélio Moiane
 *
 */
public class Payment {

	private Voucher voucher;

	private String mpesaNumber;

	private BigDecimal discountValue;

	private BigDecimal total;

	private String status;

	private String statusDescription;

	private String unitUuid;

	public Payment() {
	}

	public Payment(final Voucher voucher) {
		this.voucher = voucher;

		final Discount discount = this.voucher.getDiscount();
		this.discountValue = discount.calculate();
		this.total = discount.getVoucher().subtract(this.discountValue);
	}

	public Voucher getVoucher() {
		return this.voucher;
	}

	public String getMpesaNumber() {
		return this.mpesaNumber;
	}

	public BigDecimal getDiscountValue() {
		return this.discountValue;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatusDescription(final String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getStatusDescription() {
		return this.statusDescription;
	}

	public String getUnitUuid() {
		return this.unitUuid;
	}

	public BigDecimal getVoucherValue() {
		return this.voucher.getDiscount().getVoucher();
	}
}
