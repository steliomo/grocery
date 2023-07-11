/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.item.Item;
import mz.co.grocery.core.domain.item.ItemType;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItem extends Domain implements Item {

	private ServiceDescription serviceDescription;

	private Unit unit;

	private BigDecimal salePrice;

	public Optional<ServiceDescription> getServiceDescription() {
		return Optional.ofNullable(this.serviceDescription);
	}

	public void setServiceDescription(final ServiceDescription serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	@Override
	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(final BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	@Override
	public ItemType getType() {
		return ItemType.SERVICE;
	}

	@Override
	public Boolean isStockable() {
		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return this.serviceDescription.getName();
	}

	@Override
	public BigDecimal getRentPrice() {
		return this.salePrice;
	}
}
