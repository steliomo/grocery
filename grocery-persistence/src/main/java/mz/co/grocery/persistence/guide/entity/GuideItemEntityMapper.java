/**
 *
 */
package mz.co.grocery.persistence.guide.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.rent.entity.RentItemEntity;
import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class GuideItemEntityMapper extends AbstractEntityMapper<GuideItemEntity, GuideItem> implements EntityMapper<GuideItemEntity, GuideItem> {

	private EntityMapper<GuideEntity, Guide> guideMapper;

	private EntityMapper<RentItemEntity, RentItem> rentItemMapper;

	private EntityMapper<SaleItemEntity, SaleItem> saleItemMapper;

	public GuideItemEntityMapper(final EntityMapper<GuideEntity, Guide> guideMapper, final EntityMapper<RentItemEntity, RentItem> rentItemMapper,
			final EntityMapper<SaleItemEntity, SaleItem> saleItemMapper) {
		this.guideMapper = guideMapper;
		this.rentItemMapper = rentItemMapper;
		this.saleItemMapper = saleItemMapper;
	}

	@Override
	public GuideItemEntity toEntity(final GuideItem domain) {
		final GuideItemEntity entity = new GuideItemEntity();

		domain.getGuide().ifPresent(guide -> entity.setGuide(this.guideMapper.toEntity(guide)));
		domain.getRentItem().ifPresent(rentItem -> entity.setRentItem(this.rentItemMapper.toEntity(rentItem)));
		domain.getSaleItem().ifPresent(saleItem -> entity.setSaleItem(this.saleItemMapper.toEntity(saleItem)));

		entity.setQuantity(domain.getQuantity());

		return this.toEntity(entity, domain);
	}

	@Override
	public GuideItem toDomain(final GuideItemEntity entity) {
		final GuideItem domain = new GuideItem();

		entity.getGuide().ifPresent(guide -> domain.setGuide(this.guideMapper.toDomain(guide)));
		entity.getRentItem().ifPresent(rentItem -> domain.setRentItem(this.rentItemMapper.toDomain(rentItem)));
		entity.getSaleItem().ifPresent(saleItem -> domain.setSaleItem(this.saleItemMapper.toDomain(saleItem)));

		domain.setQuantity(entity.getQuantity());

		return this.toDomain(entity, domain);
	}
}
