/**
 *
 */
package mz.co.grocery.persistence.quotation.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
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
public class QuotationItemEntityMapper extends AbstractEntityMapper<QuotationItemEntity, QuotationItem>
implements EntityMapper<QuotationItemEntity, QuotationItem> {

	private EntityMapper<QuotationEntity, Quotation> quotationMapper;

	private EntityMapper<StockEntity, Stock> stockMapper;

	private EntityMapper<ServiceItemEntity, ServiceItem> serviceItemMapper;

	public QuotationItemEntityMapper(final EntityMapper<QuotationEntity, Quotation> quotationMapper,
			final EntityMapper<StockEntity, Stock> stockMapper,
			final EntityMapper<ServiceItemEntity, ServiceItem> serviceItemMapper) {
		this.quotationMapper = quotationMapper;
		this.stockMapper = stockMapper;
		this.serviceItemMapper = serviceItemMapper;
	}

	@Override
	public QuotationItemEntity toEntity(final QuotationItem domain) {
		final QuotationItemEntity entity = new QuotationItemEntity();

		domain.getQuotation().ifPresent(quotation -> entity.setQuotation(this.quotationMapper.toEntity(domain.getQuotation().get())));

		domain.getItem().ifPresent(item -> {

			if (item.isStockable()) {
				entity.setStock(this.stockMapper.toEntity((Stock) item));
				return;
			}

			entity.setServiceItem(this.serviceItemMapper.toEntity((ServiceItem) item));
		});

		entity.setQuantity(domain.getQuantity());
		entity.setDays(domain.getDays());

		return this.toEntity(entity, domain);
	}

	@Override
	public QuotationItem toDomain(final QuotationItemEntity entity) {
		final QuotationItem domain = new QuotationItem();

		entity.getQuotation().ifPresent(quotation -> domain.setQuotation(this.quotationMapper.toDomain(entity.getQuotation().get())));
		entity.getStock().ifPresent(stock -> domain.setItem(this.stockMapper.toDomain(stock)));
		entity.getServiceItem().ifPresent(serviceItem -> domain.setItem(this.serviceItemMapper.toDomain(serviceItem)));
		domain.setQuantity(entity.getQuantity());
		domain.setDays(entity.getDays());

		return this.toDomain(entity, domain);
	}
}
