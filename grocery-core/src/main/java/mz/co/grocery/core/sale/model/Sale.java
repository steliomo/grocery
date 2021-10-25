/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = SaleDAO.QUERY_NAME.findPerPeriod, query = SaleDAO.QUERY.findPerPeriod),
	@NamedQuery(name = SaleDAO.QUERY_NAME.findMonthlyPerPeriod, query = SaleDAO.QUERY.findMonthlyPerPeriod) })
@Entity
@Table(name = "SALES")
public class Sale extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private Grocery grocery;

	@NotNull
	@Column(name = "SALE_DATE", nullable = false)
	private LocalDate saleDate;

	@NotNull
	@Column(name = "BILLING", nullable = false)
	private BigDecimal billing = BigDecimal.ZERO;

	@NotNull
	@Column(name = "TOTAL", nullable = false)
	private BigDecimal total = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")
	private Set<SaleItem> items = new HashSet<>();

	public Grocery getGrocery() {
		return this.grocery;
	}

	public void setGrocery(final Grocery grocery) {
		this.grocery = grocery;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(final LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public Set<SaleItem> getItems() {
		return Collections.unmodifiableSet(this.items);
	}

	public void addItem(final SaleItem item) {

		if (this.items == null) {
			this.items = new HashSet<>();
		}

		this.items.add(item);
	}

	public void calculateTotal() {
		this.total = this.items.stream().map(SaleItem::getTotalSaleItem).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void calculateBilling() {
		this.billing = this.items.stream().map(SaleItem::getTotalBilling).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
