/**
 *
 */
package mz.co.grocery.core.domain.payment;

/**
 * @author Stélio Moiane
 *
 */
public enum Voucher {

	MONTHLY(new NoDiscount(new Integer(30))),

	QUARTERLY(new NoDiscount(new Integer(90))),

	SEMI_ANNUAL(new NoDiscount(new Integer(180))),

	YEARLY(new NoDiscount(new Integer(360)));

	private final Discount discount;

	Voucher(final Discount discount) {
		this.discount = discount;
	}

	public Discount getDiscount() {
		return this.discount;
	}
}
