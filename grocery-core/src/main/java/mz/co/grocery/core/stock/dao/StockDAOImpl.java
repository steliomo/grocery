/**
 *
 */
package mz.co.grocery.core.stock.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(StockDAOImpl.NAME)
public class StockDAOImpl extends GenericDAOImpl<Stock, Long> implements StockDAO {

	public static final String NAME = " mz.co.grocery.core.stock.dao.StockDAOImpl";

}
