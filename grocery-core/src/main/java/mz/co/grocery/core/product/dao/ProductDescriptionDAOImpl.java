/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ProductDescriptionDAOImpl.NAME)
public class ProductDescriptionDAOImpl extends GenericDAOImpl<ProductDescription, Long>
        implements ProductDescriptionDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ProductDescriptionDAOImpl";

	@Override
	public List<ProductDescription> fetchdAll(final int currentPage, final int maxResult,
	        final EntityStatus entityStatus) throws BusinessException {
		return this
		        .findByQuery(ProductDescriptionDAO.QUERY_NAME.fetchdAll,
		                new ParamBuilder().add("entityStatus", entityStatus).process())
		        .setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long count(final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(ProductDescriptionDAO.QUERY_NAME.count,
		        new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<ProductDescription> fetchByDescription(final String description, final EntityStatus entityStatus)
	        throws BusinessException {
		return this.findByNamedQuery(ProductDescriptionDAO.QUERY_NAME.fetchByDescription, new ParamBuilder()
		        .add("description", "%" + description + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public ProductDescription fetchByUuid(final String productDescriptionUuid, final EntityStatus entityStatus)
	        throws BusinessException {
		return this.findSingleByNamedQuery(ProductDescriptionDAO.QUERY_NAME.fetchByUuid, new ParamBuilder()
		        .add("productDescriptionUuid", productDescriptionUuid).add("entityStatus", entityStatus).process());
	}

}
