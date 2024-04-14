/**
 *
 */
package mz.co.grocery.persistence.unit.adapter;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.application.unit.out.UnitUserPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.unit.UnitDetail;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.persistence.unit.entity.UnitUserEntity;
import mz.co.grocery.persistence.unit.repository.UnitUserRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class UnitUserAdapter extends AbstractService implements UnitUserPort {

	private UnitUserRepository repository;

	private EntityMapper<UnitUserEntity, UnitUser> mapper;

	public UnitUserAdapter(
			final UnitUserRepository repository,
			final EntityMapper<UnitUserEntity, UnitUser> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public UnitUser createUnitUser(final UserContext userContext, final UnitUser unitUser)
			throws BusinessException {
		final UnitUserEntity entity = this.mapper.toEntity(unitUser);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<UnitUser> fetchAllUnitUsers(final int currentPage, final int maxResult) throws BusinessException {
		return this.repository.fetchAllGroceryUsers(currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long count() throws BusinessException {
		return this.repository.count();
	}

	@Override
	public UnitUser fetchUnitUserByUser(final String user) throws BusinessException {
		return this.mapper.toDomain(this.repository.fetchByUser(user, EntityStatus.ACTIVE));
	}

	@Override
	public UnitDetail findUnitDetailsByUuid(final String unitUuid) throws BusinessException {
		return this.repository.findUnitDetailByUuid(unitUuid, EntityStatus.ACTIVE);
	}
}
