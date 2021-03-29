/**
 *
 */
package mz.co.grocery.core.stock.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.grocery.core.stock.dao.ServiceItemDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = ServiceItemDAO.QUERY_NAME.findAllIds, query = ServiceItemDAO.QUERY.findAllIds),
	@NamedQuery(name = ServiceItemDAO.QUERY_NAME.fetchAll, query = ServiceItemDAO.QUERY.fetchAll),
	@NamedQuery(name = ServiceItemDAO.QUERY_NAME.fetchByUuid, query = ServiceItemDAO.QUERY.fetchByUuid),
	@NamedQuery(name = ServiceItemDAO.QUERY_NAME.fetchByName, query = ServiceItemDAO.QUERY.fetchByName),
	@NamedQuery(name = ServiceItemDAO.QUERY_NAME.fetchByServiceAndUnit, query = ServiceItemDAO.QUERY.fetchByServiceAndUnit),
	@NamedQuery(name = ServiceItemDAO.QUERY_NAME.fetchNotInThisUnitByService, query = ServiceItemDAO.QUERY.fetchNotInThisUnitByService) })
@Entity
@Table(name = "SERVICE_ITEMS")
public class ServiceItem extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_DESCRIPTION_ID", nullable = false)
	private ServiceDescription serviceDescription;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Grocery unit;

	@NotNull
	@Column(name = "SALE_PRICE", nullable = false)
	private BigDecimal salePrice;

	public ServiceDescription getServiceDescription() {
		return this.serviceDescription;
	}

	public void setServiceDescription(final ServiceDescription serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public Grocery getUnit() {
		return this.unit;
	}

	public void setUnit(final Grocery unit) {
		this.unit = unit;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(final BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
}
