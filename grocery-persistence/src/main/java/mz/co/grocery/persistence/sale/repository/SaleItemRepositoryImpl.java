/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class SaleItemRepositoryImpl extends GenericDAOImpl<SaleItemEntity, Long> implements SaleItemRepository {
}
