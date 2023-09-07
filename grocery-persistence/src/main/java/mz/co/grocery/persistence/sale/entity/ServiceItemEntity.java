/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.LazyInitializationException;

import mz.co.grocery.persistence.item.entity.ServiceDescriptionEntity;
import mz.co.grocery.persistence.sale.repository.ServiceItemRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = ServiceItemRepository.QUERY_NAME.findAllIds, query = ServiceItemRepository.QUERY.findAllIds),
	@NamedQuery(name = ServiceItemRepository.QUERY_NAME.fetchAll, query = ServiceItemRepository.QUERY.fetchAll),
	@NamedQuery(name = ServiceItemRepository.QUERY_NAME.fetchByUuid, query = ServiceItemRepository.QUERY.fetchByUuid),
	@NamedQuery(name = ServiceItemRepository.QUERY_NAME.fetchByName, query = ServiceItemRepository.QUERY.fetchByName),
	@NamedQuery(name = ServiceItemRepository.QUERY_NAME.fetchByServiceAndUnit, query = ServiceItemRepository.QUERY.fetchByServiceAndUnit),
	@NamedQuery(name = ServiceItemRepository.QUERY_NAME.fetchNotInThisUnitByService, query = ServiceItemRepository.QUERY.fetchNotInThisUnitByService) })
@Entity
@Table(name = "SERVICE_ITEMS")
public class ServiceItemEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_DESCRIPTION_ID", nullable = false)
	private ServiceDescriptionEntity serviceDescription;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private UnitEntity unit;

	@NotNull
	@Column(name = "SALE_PRICE", nullable = false)
	private BigDecimal salePrice;

	public Optional<ServiceDescriptionEntity> getServiceDescription() {
		try {
			Optional.ofNullable(this.serviceDescription).ifPresent(serviceDescription -> serviceDescription.getDescription());
			return Optional.of(this.serviceDescription);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setServiceDescription(final ServiceDescriptionEntity serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public Optional<UnitEntity> getUnit() {
		try {
			Optional.ofNullable(this.unit).ifPresent(unit -> unit.getName());
			return Optional.ofNullable(this.unit);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(final BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
}
