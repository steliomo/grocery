/**
 *
 */
package mz.co.grocery.core.grocery.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.dao.GroceryDAO;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(GroceryServiceImpl.NAME)
public class GroceryServiceImpl extends AbstractService implements GroceryService {
	public static final String NAME = "mz.co.grocery.core.grocery.service.GroceryServiceImpl";

	@Inject
	private GroceryDAO groceryDAO;

	@Override
	public Grocery createGrocery(final UserContext userContext, final Grocery grocery) throws BusinessException {
		this.groceryDAO.create(userContext, grocery);
		return grocery;
	}
}
