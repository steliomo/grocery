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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = SaleDAO.QUERY_NAME.findLast7DaysSale, query = SaleDAO.QUERY.findLast7DaysSale) })
@Entity
@Table(name = "SALES")
public class Sale extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "SALE_DATE", nullable = false)
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate saleDate;

	@NotNull
	@Column(name = "PROFIT", nullable = false)
	private BigDecimal profit = BigDecimal.ZERO;

	@NotNull
	@Column(name = "TOTAL", nullable = false)
	private BigDecimal total = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")
	private Set<SaleItem> items;

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(final LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getProfit() {
		return this.profit;
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

	public void calculateProfit() {
		this.profit = this.items.stream().map(SaleItem::getTotalProfit).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
