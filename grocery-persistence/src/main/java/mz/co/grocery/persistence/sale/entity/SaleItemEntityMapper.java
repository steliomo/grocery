/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class SaleItemEntityMapper extends AbstractEntityMapper<SaleItemEntity, SaleItem> implements EntityMapper<SaleItemEntity, SaleItem> {

	private EntityMapper<SaleEntity, Sale> saleMapper;

	private EntityMapper<StockEntity, Stock> stockMapper;

	private EntityMapper<ServiceItemEntity, ServiceItem> serviceItemMapper;

	public SaleItemEntityMapper(final EntityMapper<SaleEntity, Sale> saleMapper, final EntityMapper<StockEntity, Stock> stockMapper,
			final EntityMapper<ServiceItemEntity, ServiceItem> serviceItemMapper) {
		this.saleMapper = saleMapper;
		this.stockMapper = stockMapper;
		this.serviceItemMapper = serviceItemMapper;
	}

	@Override
	public SaleItemEntity toEntity(final SaleItem domain) {

		final SaleItemEntity entity = new SaleItemEntity();

		domain.getSale().ifPresent(sale -> entity.setSale(this.saleMapper.toEntity(sale)));
		domain.getStock().ifPresent(stock -> entity.setStock(this.stockMapper.toEntity(stock)));
		domain.getServiceItem().ifPresent(serviceItem -> entity.setServiceItem(this.serviceItemMapper.toEntity(serviceItem)));

		entity.setQuantity(domain.getQuantity());
		entity.setSaleItemValue(domain.getSaleItemValue());
		entity.setDiscount(domain.getDiscount());
		entity.setDeliveredQuantity(domain.getDeliveredQuantity());
		entity.setDeliveryStatus(domain.getDeliveryStatus());

		return this.toEntity(entity, domain);
	}

	@Override
	public SaleItem toDomain(final SaleItemEntity entity) {

		final SaleItem domain = new SaleItem();

		entity.getSale().ifPresent(sale -> domain.setSale(this.saleMapper.toDomain(sale)));
		entity.getStock().ifPresent(stock -> domain.setStock(this.stockMapper.toDomain(stock)));
		entity.getServiceItem().ifPresent(serviceItem -> domain.setServiceItem(this.serviceItemMapper.toDomain(serviceItem)));

		domain.setQuantity(entity.getQuantity());
		domain.setSaleItemValue(entity.getSaleItemValue());
		domain.setDiscount(entity.getDiscount());
		domain.setDeliveredQuantity(entity.getDeliveredQuantity());
		domain.setDeliveryStatus(entity.getDeliveryStatus());

		return this.toDomain(entity, domain);
	}
}
