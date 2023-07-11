/**
 *
 */
package mz.co.grocery.core.domain.guide;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */

public class Guide extends Domain {

	private Rent rent;

	private Sale sale;

	private LocalDate issueDate;

	private GuideType type;

	private String filePath;

	private Set<GuideItem> guideItems;

	public Optional<Rent> getRent() {
		return Optional.ofNullable(this.rent);
	}

	public void setRent(final Rent rent) {
		this.rent = rent;
	}

	public Optional<Sale> getSale() {
		return Optional.ofNullable(this.sale);
	}

	public void setSale(final Sale sale) {
		this.sale = sale;
	}

	public LocalDate getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(final LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public GuideType getType() {
		return this.type;
	}

	public void setType(final GuideType type) {
		this.type = type;
	}

	public Optional<Set<GuideItem>> getGuideItems() {
		return Optional.ofNullable(this.guideItems);
	}

	public void addGuideItem(final GuideItem guideItem) {
		if (this.guideItems == null) {
			this.guideItems = new HashSet<>();
		}

		this.guideItems.add(guideItem);
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	public Unit getUnit() {
		return this.rent != null ? this.rent.getUnit().get() : this.sale.getUnit().get();
	}

	public Customer getCustomer() {
		return this.rent != null ? this.rent.getCustomer().get() : this.sale.getCustomer().get();
	}
}
