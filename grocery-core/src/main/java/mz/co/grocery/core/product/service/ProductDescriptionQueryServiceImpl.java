/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ProductDescriptionDAO;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductDescriptionQueryServiceImpl.NAME)
public class ProductDescriptionQueryServiceImpl implements ProductDescriptionQueryService {

	public static final String NAME = " mz.co.grocery.core.product.service.ProductDescriptionQueryServiceImpl";

	@Inject
	private ProductDescriptionDAO productDescriptionDAO;

	@Override
	public List<ProductDescription> fetchdAllProductDescriptions(final int currentPage, final int maxResult)
	        throws BusinessException {
		return this.productDescriptionDAO.fetchdAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countProductDescriptions() throws BusinessException {
		return this.productDescriptionDAO.count(EntityStatus.ACTIVE);
	}

	@Override
	public List<ProductDescription> fetchProductDescriptionByDescription(final String description)
	        throws BusinessException {
		return this.productDescriptionDAO.fetchByDescription(description, EntityStatus.ACTIVE);
	}

	@Override
	public ProductDescription fetchProductDescriptionByUuid(final String productDescriptionUuid)
	        throws BusinessException {
		return this.productDescriptionDAO.fetchByUuid(productDescriptionUuid, EntityStatus.ACTIVE);
	}
}
