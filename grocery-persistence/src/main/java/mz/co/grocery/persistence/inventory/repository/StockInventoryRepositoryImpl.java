/**
 *
 */
package mz.co.grocery.persistence.inventory.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.inventory.entity.StockInventoryEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class StockInventoryRepositoryImpl extends GenericDAOImpl<StockInventoryEntity, Long> implements StockInventoryRepository {

}
