/**
 *
 */
package mz.co.grocery.core.product.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ProductUnitDAO;
import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductUnitServiceImpl.NAME)
public class ProductUnitServiceImpl extends AbstractService implements ProductUnitService {

	public static final String NAME = "mz.co.grocery.core.product.service.ProductUnitServiceImpl";

	@Inject
	private ProductUnitDAO productUnitDAO;

	@Override
	public ProductUnit createProductUnit(final UserContext userContext, final ProductUnit productUnit)
	        throws BusinessException {
		this.productUnitDAO.create(userContext, productUnit);
		return productUnit;
	}

	@Override
	public ProductUnit updateProductUnit(final UserContext userContext, final ProductUnit productUnit)
	        throws BusinessException {
		this.productUnitDAO.update(userContext, productUnit);
		return productUnit;
	}

	@Override
	public ProductUnit removeProductUnit(final UserContext userContext, final ProductUnit productUnit)
	        throws BusinessException {
		productUnit.inactive();
		this.productUnitDAO.update(userContext, productUnit);
		return productUnit;
	}
}
