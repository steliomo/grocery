/**
 *
 */
package mz.co.grocery.core.sale.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(SaleDAOImpl.NAME)
public class SaleDAOImpl extends GenericDAOImpl<Sale, Long> implements SaleDAO {

	public static final String NAME = "mz.co.grocery.core.sale.dao.SaleDAOImpl";

}
