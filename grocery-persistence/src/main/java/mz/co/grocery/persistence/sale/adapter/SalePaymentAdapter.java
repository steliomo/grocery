/**
 *
 */
package mz.co.grocery.persistence.sale.adapter;

import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.persistence.sale.entity.SalePaymentEntity;
import mz.co.grocery.persistence.sale.repository.SalePaymentRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class SalePaymentAdapter implements SalePaymentPort {

	private SalePaymentRepository repository;

	private EntityMapper<SalePaymentEntity, SalePayment> mapper;

	public SalePaymentAdapter(final SalePaymentRepository repository,
			final EntityMapper<SalePaymentEntity, SalePayment> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SalePayment createSalePayment(final UserContext context, final SalePayment salePayment) throws BusinessException {
		final SalePaymentEntity entity = this.mapper.toEntity(salePayment);

		this.repository.create(context, entity);

		return this.mapper.toDomain(entity);
	}
}
