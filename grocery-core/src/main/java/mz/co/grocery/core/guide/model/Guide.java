/**
 *
 */
package mz.co.grocery.core.guide.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@Entity
@Table(name = "GUIDES")
public class Guide extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ID")
	private Rent rent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID")
	private Sale sale;

	@NotNull
	@Column(name = "ISSUE_DATE", nullable = false)
	private LocalDate issueDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GUIDE_TYPE", nullable = false, length = 15)
	private GuideType type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "guide")
	private Set<GuideItem> guideItems;

	public Rent getRent() {
		return this.rent;
	}

	public void setRent(final Rent rent) {
		this.rent = rent;
	}

	public Sale getSale() {
		return this.sale;
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

	public Set<GuideItem> getGuideItems() {
		return Collections.unmodifiableSet(this.guideItems);
	}

	public void addGuideItem(final GuideItem guideItem) {

		if (this.guideItems == null) {
			this.guideItems = new HashSet<>();
		}

		this.guideItems.add(guideItem);
	}

	public Grocery getUnit() {
		return this.rent != null ? this.rent.getUnit() : this.sale.getGrocery();
	}

	public Customer getCustomer() {
		return this.rent != null ? this.rent.getCustomer() : this.sale.getCustomer();
	}
}
