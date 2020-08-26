/**
 *
 */
package mz.co.grocery.core.grocery.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.dao.GroceryDAO;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(GroceryQueryServiceImpl.NAME)
public class GroceryQueryServiceImpl implements GroceryQueryService {

	public static final String NAME = "mz.co.grocery.core.grocery.service.GroceryQueryServiceImpl";

	@Inject
	private GroceryDAO groceryDAO;

	@Override
	public List<Grocery> findAllGroceries(final int currentPage, final int maxResult) throws BusinessException {
		return this.groceryDAO.findAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long count() throws BusinessException {
		return this.groceryDAO.count(EntityStatus.ACTIVE);
	}

	@Override
	public Grocery findByUuid(final String groceryUuid) throws BusinessException {
		return this.groceryDAO.findByUuid(groceryUuid);
	}

}
