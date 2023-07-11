/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class SalePaymentEntityMapper extends AbstractEntityMapper<SalePaymentEntity, SalePayment>
implements EntityMapper<SalePaymentEntity, SalePayment> {

	private EntityMapper<SaleEntity, Sale> saleMapper;

	public SalePaymentEntityMapper(final EntityMapper<SaleEntity, Sale> saleMapper) {
		this.saleMapper = saleMapper;
	}

	@Override
	public SalePaymentEntity toEntity(final SalePayment domain) {

		final SalePaymentEntity entity = new SalePaymentEntity();

		domain.getSale().ifPresent(sale -> entity.setSale(this.saleMapper.toEntity(sale)));
		entity.setPaymentValue(domain.getPaymentValue());
		entity.setPaymentDate(domain.getPaymentDate());

		return this.toEntity(entity, domain);
	}

	@Override
	public SalePayment toDomain(final SalePaymentEntity entity) {

		final SalePayment domain = new SalePayment(this.saleMapper.toDomain(entity.getSale()), entity.getPaymentValue(), entity.getPaymentDate());

		return this.toDomain(entity, domain);
	}
}
