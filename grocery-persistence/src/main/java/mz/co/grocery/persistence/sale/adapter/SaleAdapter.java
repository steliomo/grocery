/**
 *
 */
package mz.co.grocery.persistence.sale.adapter;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.sale.entity.SaleEntity;
import mz.co.grocery.persistence.sale.repository.SaleRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class SaleAdapter implements SalePort {

	private SaleRepository repository;

	private EntityMapper<SaleEntity, Sale> mapper;

	public SaleAdapter(final SaleRepository saleRepository, final EntityMapper<SaleEntity, Sale> mapper) {
		this.repository = saleRepository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Sale createSale(final UserContext context, final Sale sale) throws BusinessException {

		final SaleEntity entity = this.mapper.toEntity(sale);

		this.repository.create(context, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Sale updateSale(final UserContext context, final Sale sale) throws BusinessException {

		final SaleEntity entity = this.mapper.toEntity(sale);

		this.repository.update(context, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Sale fetchByUuid(final String uuid) throws BusinessException {
		return this.repository.fetchByUuid(uuid);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate)
			throws BusinessException {
		return this.repository.findPerPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndMonthlyPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate)
			throws BusinessException {
		return this.repository.findMonthlyPerPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<Sale> findPendingOrImpletePaymentSaleStatusByCustomer(final Customer customer) throws BusinessException {
		return this.repository.findPendingOrImpletePaymentSaleStatusByCustomer(customer.getUuid(), EntityStatus.ACTIVE);
	}

	@Override
	public List<Sale> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(final Customer customer) throws BusinessException {
		return this.repository.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(customer.getUuid(), EntityStatus.ACTIVE);
	}

	@Override
	public List<Sale> fetchSalesWithDeliveryGuidesByCustomer(final Customer customer) throws BusinessException {
		return this.repository.fetchSalesWithDeliveryGuidesByCustomer(customer.getUuid(), EntityStatus.ACTIVE);
	}

	@Override
	public Sale findByUuid(final String saleUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(saleUuid));
	}
}
