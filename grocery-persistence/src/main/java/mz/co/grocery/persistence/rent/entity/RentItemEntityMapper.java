/**
 *
 */
package mz.co.grocery.persistence.rent.entity;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class RentItemEntityMapper extends AbstractEntityMapper<RentItemEntity, RentItem> implements EntityMapper<RentItemEntity, RentItem> {

	private EntityMapper<RentEntity, Rent> rentMapper;

	private EntityMapper<StockEntity, Stock> stockMapper;

	private EntityMapper<ServiceItemEntity, ServiceItem> serviceItemMapper;

	public RentItemEntityMapper(final EntityMapper<StockEntity, Stock> stockMapper,
			final EntityMapper<ServiceItemEntity, ServiceItem> serviceItemMapper) {
		this.stockMapper = stockMapper;
		this.serviceItemMapper = serviceItemMapper;
	}

	@Override
	public RentItemEntity toEntity(final RentItem domain) {

		final RentItemEntity entity = new RentItemEntity();

		domain.getRent().ifPresent(rent -> entity.setRent(this.rentMapper.toEntity(rent)));

		domain.getItem().ifPresent(item -> {
			if (item.isStockable()) {
				entity.setStock(this.stockMapper.toEntity((Stock) item));
				return;
			}
			entity.setServiceItem(this.serviceItemMapper.toEntity((ServiceItem) item));
		});

		entity.setPlannedQuantity(domain.getPlannedQuantity());
		entity.setPlannedDays(domain.getPlannedDays());
		entity.setPlannedTotal(domain.getPlannedTotal());

		entity.setLoadedQuantity(domain.getLoadedQuantity());
		entity.setLoadingDate(domain.getLoadingDate());
		entity.setLoadStatus(domain.getLoadStatus());

		entity.setReturnedQuantity(domain.getReturnedQuantity());
		entity.setReturnDate(domain.getReturnDate());
		entity.setReturnStatus(domain.getReturnStatus());

		entity.setDiscount(domain.getDiscount());
		entity.setStockable(domain.isStockable());

		return this.toEntity(entity, domain);
	}

	@Override
	public RentItem toDomain(final RentItemEntity entity) {
		final RentItem domain = new RentItem();

		entity.getRent().ifPresent(rent -> {
			rent.noRentItems();
			rent.noGuides();
			domain.setRent(this.rentMapper.toDomain(rent));
		});

		entity.getStock().ifPresent(stock -> domain.setItem(this.stockMapper.toDomain(stock)));
		entity.getServiceItem().ifPresent(serviceItem -> domain.setItem(this.serviceItemMapper.toDomain(serviceItem)));

		domain.setPlannedQuantity(entity.getPlannedQuantity());
		domain.setPlannedDays(entity.getPlannedDays());
		domain.setPlannedTotal(entity.getPlannedTotal());

		domain.setLoadedQuantity(entity.getLoadedQuantity());
		domain.setLoadingDate(entity.getLoadingDate());
		domain.setLoadStatus(entity.getLoadStatus());

		domain.setReturnedQuantity(entity.getReturnedQuantity());
		domain.setReturnDate(entity.getReturnDate());
		domain.setReturnStatus(entity.getReturnStatus());

		domain.setDiscount(entity.getDiscount());
		domain.setStockable(entity.getStockable());

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setRentMapper(final EntityMapper<RentEntity, Rent> rentMapper) {
		this.rentMapper = rentMapper;
	}
}
