/**
 *
 */
package mz.co.grocery.persistence.guide.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.guide.entity.GuideItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class GuideItemRepositoryImpl extends GenericDAOImpl<GuideItemEntity, Long> implements GuideItemRepository {

}
