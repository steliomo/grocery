/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductDescriptionDAO extends GenericDAO<ProductDescription, Long> {

	class QUERY {
		public static final String fetchdAll = "SELECT pd FROM ProductDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE pd.entityStatus = :entityStatus GROUP BY pd.id ORDER BY pd.product.name";

		public static final String count = "SELECT COUNT(pd.id) FROM ProductDescription pd WHERE pd.entityStatus = :entityStatus";

		public static final String fetchByDescription = "SELECT pd FROM ProductDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE CONCAT(p.name, ' ', pd.description) LIKE :description AND pd.entityStatus = :entityStatus GROUP BY pd.id ORDER BY pd.product.name";

		public static final String fetchByUuid = "SELECT pd FROM ProductDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE pd.uuid = :productDescriptionUuid AND pd.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String fetchdAll = "ProductDescription.fetchdAll";

		public static final String count = "ProductDescription.count";

		public static final String fetchByDescription = "ProductDescription.fetchByDescription";

		public static final String fetchByUuid = "ProductDescription.fetchByUuid";
	}

	List<ProductDescription> fetchdAll(int currentPage, int maxResult, EntityStatus entityStatus)
			throws BusinessException;

	@Override
	Long count(EntityStatus entityStatus) throws BusinessException;

	List<ProductDescription> fetchByDescription(String description, EntityStatus entityStatus) throws BusinessException;

	ProductDescription fetchByUuid(String productDescriptionUuid, EntityStatus entityStatus) throws BusinessException;
}
