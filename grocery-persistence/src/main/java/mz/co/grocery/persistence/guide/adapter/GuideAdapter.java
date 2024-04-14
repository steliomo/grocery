/**
 *
 */
package mz.co.grocery.persistence.guide.adapter;

import mz.co.grocery.core.application.guide.out.GuidePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.persistence.guide.entity.GuideEntity;
import mz.co.grocery.persistence.guide.repository.GuideRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class GuideAdapter implements GuidePort {

	private GuideRepository repository;

	private EntityMapper<GuideEntity, Guide> mapper;

	public GuideAdapter(final GuideRepository repository, final EntityMapper<GuideEntity, Guide> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Guide createGuide(final UserContext userContext, final Guide guide) throws BusinessException {

		final GuideEntity entity = this.mapper.toEntity(guide);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}
}
