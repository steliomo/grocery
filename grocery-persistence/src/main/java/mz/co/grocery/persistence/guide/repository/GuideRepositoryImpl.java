/**
 *
 */
package mz.co.grocery.persistence.guide.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.guide.entity.GuideEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class GuideRepositoryImpl extends GenericDAOImpl<GuideEntity, Long> implements GuideRepository {

}
