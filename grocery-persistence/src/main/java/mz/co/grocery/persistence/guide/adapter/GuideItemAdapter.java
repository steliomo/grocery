/**
 *
 */
package mz.co.grocery.persistence.guide.adapter;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.guide.out.GuideItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.persistence.guide.entity.GuideItemEntity;
import mz.co.grocery.persistence.guide.repository.GuideItemRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class GuideItemAdapter implements GuideItemPort {

	private GuideItemRepository repository;

	private EntityMapper<GuideItemEntity, GuideItem> mapper;

	public GuideItemAdapter(final GuideItemRepository repository,
			final EntityMapper<GuideItemEntity, GuideItem> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public GuideItem createGuideItem(final UserContext userContext, final GuideItem guideItem) throws BusinessException {

		final GuideItemEntity entity = this.mapper.toEntity(guideItem);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

}
