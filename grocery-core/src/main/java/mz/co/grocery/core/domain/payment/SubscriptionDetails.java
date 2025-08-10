/**
 *
 */
package mz.co.grocery.core.domain.payment;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public class SubscriptionDetails {

	private Voucher voucher;

	private String walletNumber;

	private BigDecimal discountValue;

	private BigDecimal total;

	private String status;

	private String statusDescription;

	private String unitUuid;

	public SubscriptionDetails() {
	}

	public SubscriptionDetails(final Voucher voucher) {
		this.voucher = voucher;

		final Discount discount = this.voucher.getDiscount();
		this.discountValue = discount.getDiscount();
		this.total = discount.getTotal();
	}

	public Voucher getVoucher() {
		return this.voucher;
	}

	public String getWalletNumber() {
		return this.walletNumber;
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

	public Integer getDays() {
		return this.voucher.getDiscount().getDays();
	}
}
