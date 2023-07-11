/**
 *
 */
package mz.co.grocery.persistence.quotation.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.quotation.entity.QuotationItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class QuotationItemRepositoryImpl extends GenericDAOImpl<QuotationItemEntity, Long> implements QuotationItemRepository {

}
