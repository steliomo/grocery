/**
 *
 */
package mz.co.grocery.persistence.guide.entity;

import java.time.LocalDate;
import java.util.Optional;
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

import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.persistence.rent.entity.RentEntity;
import mz.co.grocery.persistence.sale.entity.SaleEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@Entity
@Table(name = "GUIDES")
public class GuideEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ID")
	private RentEntity rent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID")
	private SaleEntity sale;

	@NotNull
	@Column(name = "ISSUE_DATE", nullable = false)
	private LocalDate issueDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GUIDE_TYPE", nullable = false, length = 15)
	private GuideType type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "guide")
	private Set<GuideItemEntity> guideItems;

	public Optional<RentEntity> getRent() {
		return Optional.ofNullable(this.rent);
	}

	public void setRent(final RentEntity rent) {
		this.rent = rent;
	}

	public Optional<SaleEntity> getSale() {
		return Optional.ofNullable(this.sale);
	}

	public void setSale(final SaleEntity sale) {
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

	public Optional<Set<GuideItemEntity>> getGuideItems() {
		return Optional.ofNullable(this.guideItems);
	}

	public void setGuideItems(final Set<GuideItemEntity> guideItems) {
		this.guideItems = guideItems;
	}
}
