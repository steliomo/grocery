/**
 *
 */
package mz.co.grocery.core.sale.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(SaleItemDAOImpl.NAME)
public class SaleItemDAOImpl extends GenericDAOImpl<SaleItem, Long> implements SaleItemDAO {

	public static final String NAME = "mz.co.grocery.core.sale.service.SaleItemDAOImpl";

}
