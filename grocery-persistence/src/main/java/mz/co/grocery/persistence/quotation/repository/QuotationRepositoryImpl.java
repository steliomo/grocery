/**
 *
 */
package mz.co.grocery.persistence.quotation.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.quotation.entity.QuotationEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class QuotationRepositoryImpl extends GenericDAOImpl<QuotationEntity, Long> implements QuotationRepository {

}
