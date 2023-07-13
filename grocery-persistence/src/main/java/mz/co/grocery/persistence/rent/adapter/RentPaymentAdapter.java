/**
 *
 */
package mz.co.grocery.persistence.rent.adapter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.rent.entity.RentPaymentEntity;
import mz.co.grocery.persistence.rent.repository.RentPaymentRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class RentPaymentAdapter implements RentPaymentPort {

	private RentPaymentRepository repository;

	private EntityMapper<RentPaymentEntity, RentPayment> mapper;

	public RentPaymentAdapter(final RentPaymentRepository rentPaymentRepository,
			final EntityMapper<RentPaymentEntity, RentPayment> mapper) {
		this.repository = rentPaymentRepository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public RentPayment createRentPayment(final UserContext userContext, final RentPayment rentPayment) throws BusinessException {
		final RentPaymentEntity entity = this.mapper.toEntity(rentPayment);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate)
			throws BusinessException {
		return this.repository.findSalesByUnitAndPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndMonthlyPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate)
			throws BusinessException {
		return this.repository.findSalesByUnitAndMonthlyPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}
}
