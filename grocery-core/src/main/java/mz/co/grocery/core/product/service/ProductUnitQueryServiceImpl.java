/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ProductUnitDAO;
import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductUnitQueryServiceImpl.NAME)
public class ProductUnitQueryServiceImpl implements ProductUnitQueryService {

	public static final String NAME = "mz.co.grocery.core.product.service.ProductUnitQueryServiceImpl";

	@Inject
	private ProductUnitDAO productUnitDAO;

	@Override
	public List<ProductUnit> findAllProductUnits() throws BusinessException {
		return this.productUnitDAO.findAll(EntityStatus.ACTIVE);
	}

	@Override
	public ProductUnit findProductUnitByUuid(final String productUnitUuid) throws BusinessException {
		return this.productUnitDAO.findByUuid(productUnitUuid);
	}

}
