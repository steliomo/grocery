/**
 *
 */
package mz.co.grocery.core.rent;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.RentTemplate;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentUnitBuilder {

	private final Rent rent;

	public RentUnitBuilder() {
		this.rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID);
	}

	public RentUnitBuilder(final LocalDate localDate) {
		this.rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID);
		this.rent.setRentDate(localDate);
	}

	public RentUnitBuilder withUnloadedProducts(final int quantity) {
		final List<RentItem> products = EntityFactory.gimme(RentItem.class, quantity, RentItemTemplate.PRODUCT);
		products.forEach(product -> this.rent.addRentItem(product));
		return this;
	}

	public RentUnitBuilder withLoadedProducts(final int quantity, final LocalDate loadDate) {
		final List<RentItem> products = EntityFactory.gimme(RentItem.class, quantity, RentItemTemplate.PRODUCT, result -> {
			if (result instanceof RentItem) {
				final RentItem rentItem = (RentItem) result;
				rentItem.calculatePlannedTotal();
				rentItem.getRentalChunkValueOnLoading(loadDate);
			}
		});

		products.forEach(product -> this.rent.addRentItem(product));
		return this;
	}

	public RentUnitBuilder withUnloadedServices(final int quantity) {
		final List<RentItem> services = EntityFactory.gimme(RentItem.class, quantity, RentItemTemplate.SERVICE);
		services.forEach(service -> this.rent.addRentItem(service));
		return this;
	}

	public RentUnitBuilder calculatePlannedTotal() {
		this.rent.getRentItems().get().forEach(rentItem -> rentItem.calculatePlannedTotal());
		return this;
	}

	public Rent build() {
		return this.rent;
	}
}
