/**
 *
 */
package mz.co.grocery.persistence.sale.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleItemReport;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.grocery.persistence.sale.repository.SaleItemRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class SaleItemAdapter implements SaleItemPort {

	private SaleItemRepository repository;

	private EntityMapper<SaleItemEntity, SaleItem> mapper;

	public SaleItemAdapter(final SaleItemRepository repository,
			final EntityMapper<SaleItemEntity, SaleItem> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SaleItem createSaleItem(final UserContext context, final SaleItem saleItem) throws BusinessException {
		final SaleItemEntity entity = this.mapper.toEntity(saleItem);

		this.repository.create(context, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public SaleItem updateSaleItem(final UserContext userContext, final SaleItem saleItem) throws BusinessException {
		final SaleItemEntity entity = this.mapper.toEntity(saleItem);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public SaleItem findByUuid(final String uuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(uuid));
	}

	@Override
	public Optional<SaleItem> findBySaleAndProductUuid(final String saleUuid, final String productUuid) throws BusinessException {

		final Optional<SaleItemEntity> foundSaleItem = this.repository.findBySaleAndProductUuid(saleUuid, productUuid, EntityStatus.ACTIVE);

		if (foundSaleItem.isPresent()) {
			return Optional.of(this.mapper.toDomain(foundSaleItem.get()));
		}

		return Optional.empty();
	}

	@Override
	public Optional<SaleItem> findBySaleAndServiceUuid(final String saleUuid, final String serviceUuid) throws BusinessException {

		final Optional<SaleItemEntity> foundSaleItem = this.repository.findBySaleAndServiceUuid(saleUuid, serviceUuid, EntityStatus.ACTIVE);

		if (foundSaleItem.isPresent()) {
			return Optional.of(this.mapper.toDomain(foundSaleItem.get()));
		}

		return Optional.empty();
	}

	@Override
	public List<SaleItemReport> findSaleItemsByUnitAndPeriodAndSaleStatus(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final SaleStatus saleStatus) throws BusinessException {
		return this.repository.findSaleItemsByUnitAndPeriodAndSaleStatus(unitUuid, startDate, endDate, saleStatus, EntityStatus.ACTIVE);
	}
}
