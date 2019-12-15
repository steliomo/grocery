/**
 *
 */
package mz.co.grocery.core.inventory.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(StockInventoryDAOImpl.NAME)
public class StockInventoryDAOImpl extends GenericDAOImpl<StockInventory, Long> implements StockInventoryDAO {

	public static final String NAME = "mz.co.grocery.core.inventory.dao.StockInventoryDAOImpl";

}
