/**
 *
 */
package mz.co.grocery.persistence.guide.entity;

import java.util.HashSet;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.rent.entity.RentEntity;
import mz.co.grocery.persistence.sale.entity.SaleEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class GuideEntityMapper extends AbstractEntityMapper<GuideEntity, Guide> implements EntityMapper<GuideEntity, Guide> {

	private EntityMapper<RentEntity, Rent> rentMapper;

	private EntityMapper<SaleEntity, Sale> saleMapper;

	private EntityMapper<GuideItemEntity, GuideItem> guideItemMapper;

	@Override
	public GuideEntity toEntity(final Guide domain) {
		final GuideEntity entity = new GuideEntity();

		domain.getRent().ifPresent(rent -> entity.setRent(this.rentMapper.toEntity(rent)));
		domain.getSale().ifPresent(sale -> entity.setSale(this.saleMapper.toEntity(sale)));

		entity.setIssueDate(domain.getIssueDate());
		entity.setType(domain.getType());

		return this.toEntity(entity, domain);
	}

	@Override
	public Guide toDomain(final GuideEntity entity) {
		final Guide domain = new Guide();

		entity.getRent().ifPresent(rent -> domain.setRent(this.rentMapper.toDomain(rent)));
		entity.getSale().ifPresent(sale -> domain.setSale(this.saleMapper.toDomain(sale)));

		domain.setIssueDate(entity.getIssueDate());
		domain.setType(entity.getType());

		entity.getGuideItems().ifPresent(items -> items.forEach(item -> {
			item.getGuide().get().setGuideItems(new HashSet<>());
			item.getSaleItem().ifPresent(saleItem -> saleItem.getSale().get().setItems(new HashSet<>()));
			domain.addGuideItem(this.guideItemMapper.toDomain(item));
		}));

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setRentMapper(final EntityMapper<RentEntity, Rent> rentMapper) {
		this.rentMapper = rentMapper;
	}

	@Inject
	public void setSaleMapper(final EntityMapper<SaleEntity, Sale> saleMapper) {
		this.saleMapper = saleMapper;
	}

	@Inject
	public void setGuideItemMapper(final EntityMapper<GuideItemEntity, GuideItem> guideItemMapper) {
		this.guideItemMapper = guideItemMapper;
	}
}
