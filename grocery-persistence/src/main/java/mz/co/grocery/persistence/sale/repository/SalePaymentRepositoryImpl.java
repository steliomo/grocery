/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.sale.entity.SalePaymentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class SalePaymentRepositoryImpl extends GenericDAOImpl<SalePaymentEntity, Long> implements SalePaymentRepository {

}
