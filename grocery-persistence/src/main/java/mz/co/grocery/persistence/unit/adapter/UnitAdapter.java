/**
 *
 */
package mz.co.grocery.persistence.unit.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.grocery.persistence.unit.repository.UnitRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class UnitAdapter implements UnitPort {

	private UnitRepository repository;

	private EntityMapper<UnitEntity, Unit> mapper;

	public UnitAdapter(final UnitRepository repository, final EntityMapper<UnitEntity, Unit> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Unit createUnit(final UserContext userContext, final Unit unit) throws BusinessException {

		final UnitEntity entity = this.mapper.toEntity(unit);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Unit updateUnit(final UserContext context, final Unit unit) throws BusinessException {

		final UnitEntity entity = this.mapper.toEntity(unit);

		this.repository.update(context, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<Unit> findAllGroceries(final int currentPage, final int maxResult) throws BusinessException {
		return this.repository.findAll(currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public Long count() throws BusinessException {
		return this.repository.count(EntityStatus.ACTIVE);
	}

	@Override
	public Unit findByUuid(final String groceryUuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(groceryUuid));
	}

	@Override
	public List<Unit> findUnitsByName(final String unitName) throws BusinessException {
		return this.repository.findByName(unitName, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}
}
