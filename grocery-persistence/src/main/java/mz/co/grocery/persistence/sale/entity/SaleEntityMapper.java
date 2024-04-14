/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import java.util.HashSet;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.guide.entity.GuideEntity;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class SaleEntityMapper extends AbstractEntityMapper<SaleEntity, Sale> implements EntityMapper<SaleEntity, Sale> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<CustomerEntity, Customer> customerMapper;

	private EntityMapper<SaleItemEntity, SaleItem> saleItemMapper;

	private EntityMapper<GuideEntity, Guide> guideMapper;

	public SaleEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper,
			final EntityMapper<CustomerEntity, Customer> customerMapper) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
	}

	@Override
	public SaleEntity toEntity(final Sale domain) {
		final SaleEntity entity = new SaleEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getCustomer().ifPresent(customer -> entity.setCustomer(this.customerMapper.toEntity(customer)));

		entity.setSaleDate(domain.getSaleDate());
		entity.setBilling(domain.getBilling());
		entity.setTotal(domain.getTotal());
		entity.setSaleType(domain.getSaleType());
		entity.setSaleStatus(domain.getSaleStatus());
		entity.setTotalPaid(domain.getTotalPaid());
		entity.setDueDate(domain.getDueDate());
		entity.setDeliveryStatus(domain.getDeliveryStatus());
		entity.setTableNumber(domain.getTableNumber());

		return this.toEntity(entity, domain);
	}

	@Override
	public Sale toDomain(final SaleEntity entity) {
		final Sale domain = new Sale();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		entity.getCustomer().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		entity.getItems().ifPresent(items -> items.forEach(item -> {
			item.getSale().get().setItems(new HashSet<>());
			domain.addItem(this.saleItemMapper.toDomain(item));
		}));

		entity.getGuides().ifPresent(guides -> {
			guides.forEach(guide -> {
				guide.getSale().get().setGuides(new HashSet<>());
				domain.addGuide(this.guideMapper.toDomain(guide));
			});
		});

		domain.setSaleDate(entity.getSaleDate());
		domain.setBilling(entity.getBilling());
		domain.setTotal(entity.getTotal());
		domain.setSaleType(entity.getSaleType());
		domain.setSaleStatus(entity.getSaleStatus());
		domain.setTotalPaid(entity.getTotalPaid());
		domain.setDueDate(entity.getDueDate());
		domain.setDeliveryStatus(entity.getDeliveryStatus());
		domain.setTableNumber(entity.getTableNumber());

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setSaleItemMapper(final EntityMapper<SaleItemEntity, SaleItem> saleItemMapper) {
		this.saleItemMapper = saleItemMapper;
	}

	@Inject
	public void setGuideMapper(final EntityMapper<GuideEntity, Guide> guideMapper) {
		this.guideMapper = guideMapper;
	}
}
